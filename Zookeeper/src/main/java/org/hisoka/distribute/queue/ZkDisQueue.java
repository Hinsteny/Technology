package org.hisoka.distribute.queue;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * distribute queue example
 *
 * @Auther hinsteny
 * @Date 2017-03-06
 * @copyright: 2017 All rights reserved.
 */
public class ZkDisQueue {

    protected static final Logger logger = LoggerFactory.getLogger(ZkDisQueue.class);

    private static final String[] servers = {"127.0.0.1:2181", "127.0.0.1:2182", "127.0.0.1:2183"};
    private static final int serverNum = 3;
    private static final int SESSIONTIMEOUT = 60000;
    private static final String  QUEUENAME= "/queue";
    private static final String  QUEUESTART= "/queue/start";

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0)
            args = new String[] {"0", "1", "2"};

        ZooKeeper zk;
        for (String item : args) {
            int index = Integer.parseInt(item);
            zk = getZookeeperCon(servers[index], SESSIONTIMEOUT, new MyWatcher());
            initQueue(zk, QUEUENAME);
            joinQueue(zk, UUID.randomUUID().toString());
        }

    }

    /**
     * Initialize the queue
     * @param zk
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void initQueue(ZooKeeper zk, String queue) throws KeeperException, InterruptedException {
        logger.info("WATCH => {}", QUEUESTART);
        zk.exists(QUEUESTART, true);

        if (zk.exists(queue, false) == null) {
            logger.info("create /queue task-queue");
            zk.create(queue, "task-queue".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            logger.info("{} is exist!", queue);
        }
    }

    /**
     * Add child node to queue
     * @param zk
     * @param id
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void joinQueue(ZooKeeper zk, String id) throws KeeperException, InterruptedException {
        logger.info("create /queue/ID_{}", id);
        zk.create("/queue/ID_" + id, id.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        isCompleted(zk);
    }

    /**
     * show zk queue status
     * @param zk
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void isCompleted(ZooKeeper zk) throws KeeperException, InterruptedException {
        List<String> childs = zk.getChildren("/queue", true);
        logger.info("Show children size ===> {}", childs.size());
        if (childs.size() >= serverNum) {
            logger.info("created {} start!", QUEUESTART);
            zk.create(QUEUESTART, "start".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            // wait some time to watcher inform client, then delete all queue node
            Thread.sleep(10000);
//            deleteNode(zk, QUEUENAME);
        }

    }

    /**
     * Delete node
     * @param zk
     * @param nodePath
     */
    public static void deleteNode (ZooKeeper zk, String nodePath)  {
        try {
            if (zk.exists(nodePath, true) != null) {
                List<String> childs = zk.getChildren(nodePath, true);
                for (String child:childs) {
                    String childPath = nodePath + "/" +child;
                    deleteNode(zk, childPath);
                }
                zk.delete(nodePath, -1);
                logger.info("Show all node children ===> {}", zk.getChildren("/", true));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create one new zk connection
     * @param connectString
     * @param sessionTimeout
     * @param watcher
     * @return
     */
    private static ZooKeeper getZookeeperCon(String connectString, int sessionTimeout, Watcher watcher) {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(connectString, sessionTimeout, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return zk;
    }

    /**
     * MyWatcher for oversee if queue is complete!
     */
    private static class MyWatcher implements Watcher {
        private Logger logger = LoggerFactory.getLogger(this.getClass());

        public void process(WatchedEvent event) {
            logger.info("Received an event, path: {}, type: {} , keeperState: {} ", event.getPath(), event.getType(), event.getState().name());
            if (event.getType() == Event.EventType.NodeCreated && event.getPath().equals("/queue/start")) {
                logger.info("Queue has Completed and 'start' node is created. Finish test! ");
            }
        }
    }

}
