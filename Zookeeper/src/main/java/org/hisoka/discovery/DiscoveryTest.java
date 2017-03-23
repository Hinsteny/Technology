package org.hisoka.discovery;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.hisoka.base.ZkSever;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Hinsteny
 * @Describtion zk实现服务注册发现测试
 * @date 2017/3/23
 * @copyright: 2016 All rights reserved.
 */
public class DiscoveryTest {

    private static final String PATH = "/discovery/example";

    public static void main(String[] args) throws Exception {
        // This method is scaffolding to get the example up and running
//        TestingServer server = new TestingServer();
        ZkSever zkSever = new ZkSever("127.0.0.1", "2181");
        CuratorFramework client = null;
        ServiceDiscovery<InstanceDetails> serviceDiscovery = null;
        Map<String, ServiceProvider<InstanceDetails>> providers = Maps.newHashMap();
        try {
            client = CuratorFrameworkFactory.newClient(zkSever.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();
            JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
            serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(client).basePath(PATH).serializer(serializer).build();
            serviceDiscovery.start();
            processCommands(serviceDiscovery, providers, client);
        } finally {
            providers.values().forEach((server)->  CloseableUtils.closeQuietly(server));
            CloseableUtils.closeQuietly(serviceDiscovery);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(zkSever);
        }
    }

    /**
     * 命令行持续交互操作, 进行服务的增删查
     * @param serviceDiscovery
     * @param providers
     * @param client
     * @throws Exception
     */
    private static void processCommands(ServiceDiscovery<InstanceDetails> serviceDiscovery, Map<String, ServiceProvider<InstanceDetails>> providers, CuratorFramework client) throws Exception {
        // More scaffolding that does a simple command line processor
        printHelp();
        List<MyServer> servers = Lists.newArrayList();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            boolean done = false;
            while (!done) {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                String command = line.trim();
                String[] parts = command.split("\\s");
                if (parts.length == 0) {
                    continue;
                }
                String operation = parts[0];
                String args[] = Arrays.copyOfRange(parts, 1, parts.length);
                if (operation.equalsIgnoreCase("help") || operation.equalsIgnoreCase("?")) {
                    printHelp();
                } else if (operation.equalsIgnoreCase("q") || operation.equalsIgnoreCase("quit")) {
                    done = true;
                } else if (operation.equals("add")) {
                    addInstance(args, client, command, servers);
                } else if (operation.equals("delete")) {
                    deleteInstance(args, command, servers);
                } else if (operation.equals("random")) {
                    listRandomInstance(args, serviceDiscovery, providers, command);
                } else if (operation.equals("list")) {
                    listInstances(serviceDiscovery);
                }
            }
        } finally {
            servers.forEach((server) -> CloseableUtils.closeQuietly(server));
        }
    }

    private static void listRandomInstance(String[] args, ServiceDiscovery<InstanceDetails> serviceDiscovery, Map<String, ServiceProvider<InstanceDetails>> providers, String command) throws Exception {
        // this shows how to use a ServiceProvider
        // in a real application you'd create the ServiceProvider early for the service(s) you're interested in
        if (args.length != 1) {
            System.err.println("syntax error (expected random <name>): " + command);
            return;
        }
        String serviceName = args[0];
        ServiceProvider<InstanceDetails> provider = providers.get(serviceName);
        if (provider == null) {
            provider = serviceDiscovery.serviceProviderBuilder().serviceName(serviceName).providerStrategy(new RandomStrategy<>()).build();
            providers.put(serviceName, provider);
            provider.start();
            Thread.sleep(2500); // give the provider time to warm up - in a real application you wouldn't need to do this
        }
        ServiceInstance<InstanceDetails> instance = provider.getInstance();
        if (instance == null) {
            System.err.println("No instances named: " + serviceName);
        } else {
            outputInstance(instance);
        }
    }

    private static void listInstances(ServiceDiscovery<InstanceDetails> serviceDiscovery) throws Exception {
        // This shows how to query all the instances in service discovery
        try {
            Collection<String> serviceNames = serviceDiscovery.queryForNames();
            System.out.println(serviceNames.size() + " type(s)");
            for (String serviceName : serviceNames) {
                Collection<ServiceInstance<InstanceDetails>> instances = serviceDiscovery.queryForInstances(serviceName);
                System.out.println(serviceName);
                instances.forEach((instance) -> outputInstance(instance));
            }
        } finally {
            CloseableUtils.closeQuietly(serviceDiscovery);
        }
    }

    private static void outputInstance(ServiceInstance<InstanceDetails> instance) {
        System.out.println("\t" + instance.getPayload().getDescription() + ": " + instance.buildUriSpec());
    }

    private static void deleteInstance(String[] args, String command, List<MyServer> servers) {
        // simulate a random instance going down
        // in a real application, this would occur due to normal operation, a crash, maintenance, etc.
        if (args.length != 1) {
            System.err.println("syntax error (expected delete <name>): " + command);
            return;
        }
        final String serviceName = args[0];
        MyServer server = Iterables.find(servers, (ser) -> ser.getThisInstance().getName().endsWith(serviceName), null);
        if (server == null) {
            System.err.println("No servers found named: " + serviceName);
            return;
        }
        servers.remove(server);
        CloseableUtils.closeQuietly(server);
        System.out.println("Removed a random instance of: " + serviceName);
    }

    private static void addInstance(String[] args, CuratorFramework client, String command, List<MyServer> servers) throws Exception {
        // simulate a new instance coming up
        // in a real application, this would be a separate process
        if (args.length < 2) {
            System.err.println("syntax error (expected add <name> <description>): " + command);
            return;
        }
        StringBuilder description = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            if (i > 1) {
                description.append(' ');
            }
            description.append(args[i]);
        }
        String serviceName = args[0];
        MyServer server = new MyServer(client, PATH, serviceName, description.toString());
        servers.add(server);
        server.start();
        System.out.println(serviceName + " added");
    }

    private static void printHelp() {
        System.out.println("An example of using the ServiceDiscovery APIs. This example is driven by entering commands at the prompt:\n");
        System.out.println("add <name> <description>: Adds a mock service with the given name and description");
        System.out.println("delete <name>: Deletes one of the mock services with the given name");
        System.out.println("list: Lists all the currently registered services");
        System.out.println("random <name>: Lists a random instance of the service with the given name");
        System.out.println("quit: Quit the example");
        System.out.println();
    }
}
