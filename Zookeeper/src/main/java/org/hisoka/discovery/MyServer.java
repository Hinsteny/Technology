package org.hisoka.discovery;

import javafx.scene.control.TextFormatter;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author Hinsteny
 * @Describtion: This shows a very simplified method of registering an instance with the service discovery. Each individual
 * instance in your distribute set of applications would create an instance of something similar to ExampleServe
 * start it when the application comes up and close it when the application shuts down.
 *
 * @date 2017/3/23
 * @copyright: 2016 All rights reserved.
 */
public class MyServer implements Closeable {

    private final static String scheme = "http";
    private final static String host = "hinsteny";
    private final static int port = 8888;

    private final ServiceDiscovery<InstanceDetails> serviceDiscovery;
    private final ServiceInstance<InstanceDetails> thisInstance;

    public MyServer(CuratorFramework client, String path, String serviceName, String description) throws Exception {
        // in a real application, you'd have a convention of some kind for the URI layout
        String url = MessageFormat.format("{0}://{1}.com:{2}", scheme, host, String.valueOf(port));
        UriSpec uriSpec = new UriSpec(url);
        thisInstance = ServiceInstance.<InstanceDetails>builder()
                .name(serviceName)
                .payload(new InstanceDetails(description))
                .port(port) // in a real application, you'd use a common port
                .uriSpec(uriSpec)
                .build();
        // if you mark your payload class with @JsonRootName the provided JsonInstanceSerializer will work
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
                serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                        .client(client)
                        .basePath(path)
                        .serializer(serializer)
                        .thisInstance(thisInstance)
                        .build();
    }

    public ServiceInstance<InstanceDetails> getThisInstance() {
        return thisInstance;
    }

    public void start() throws Exception {
        serviceDiscovery.start();
    }

    @Override
    public void close() throws IOException {
        CloseableUtils.closeQuietly(serviceDiscovery);
    }
}
