package org.hisoka.base;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * single server example
 *
 * @Auther hinsteny
 * @Date 2017-03-04
 * @copyright: 2017 All rights reserved.
 */
public class ZkBasic {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int sessionTimeout = 60000;

    public static void main(String[] args){
        ZkBasic zkBasic = new ZkBasic();
        ZooKeeper zk = getZookeeperCon("127.0.0.1:2181", sessionTimeout, new MyWatcher());
        zkBasic.createNode(zk, "/hisoka", "welcome");
        zkBasic.changeNodeVal(zk, "/hisoka", " happy to see you!");
        zkBasic.deleteNode(zk, "/hisoka");
    }

    /**
     * Create zk node with path and node_value
     * @param zk
     * @param nodePath
     * @param nodeVal
     */
    public void createNode (ZooKeeper zk, String nodePath, String nodeVal)  {
        try {
            if (zk.exists(nodePath, true) == null) {
                zk.create(nodePath, nodeVal.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.info("Create path node {} and value is {}", nodePath, nodeVal);
                logger.info("get path node {} ===> {}", nodePath, new String(zk.getData(nodePath, false, null)));
                logger.info("Show all node children ===> {}", zk.getChildren("/", true));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change node value if exist
     * @param zk
     * @param nodePath
     * @param nodeVal
     * @return
     */
    public boolean changeNodeVal (ZooKeeper zk, String nodePath, String nodeVal) {
        boolean flag = false;
        try {
            if (zk.exists(nodePath, true) != null) {
                zk.setData(nodePath, nodeVal.getBytes(), -1);
                logger.info("Change path node {} and value is {}", nodePath, nodeVal);
                logger.info("get path node {} ===> {}", nodePath, new String(zk.getData(nodePath, false, null)));
                logger.info("Show all node children ===> {}", zk.getChildren("/", true));
            }
            flag = true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    /**
     * Delete node
     * @param zk
     * @param nodePath
     */
    public void deleteNode (ZooKeeper zk, String nodePath)  {
        try {
            if (zk.exists(nodePath, true) != null) {
                zk.delete(nodePath, -1);
                logger.info("Show all node children ===> {}", zk.getChildren("/", true));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ZooKeeper getZookeeperCon(String connectString, int sessionTimeout, Watcher watcher) {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(connectString, sessionTimeout, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return zk;
    }

    private static class MyWatcher implements Watcher {
        private Logger logger = LoggerFactory.getLogger(this.getClass());

        public void process(WatchedEvent watchedEvent) {
            logger.info("Received an event, path: {}, type: {} ", watchedEvent.getPath(), watchedEvent.getType());
        }
    }


}
