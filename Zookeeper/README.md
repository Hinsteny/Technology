<<<<<<< Updated upstream
# ZooKeeper
ZooKeeper是一个开源的、高性能的、分布式的分布式应用协调服务; 它提供的服务有配置维护、命名服务、分布式同步、组服务等; 常见的应用场景有一致性协议、集群管理、同步锁、Leader 选举、队列管理等.

### 概念介绍

### The ZooKeeper Data Model (数据模型)

ZooKeeper 有类似于分布式文件系统的继承的命名空间,唯一的区别是命名空间中的每个节点(ZNodes)都可以具有与其关联的数据以及子节点。节点的路径一定是规范，斜杠分隔的绝对路径, 不允许相对路径, 并且路径中运行大部分unicode字符:
1. The null character (\u0000) cannot be part of a path name. (This causes problems with the C binding.)
2. The following characters can't be used because they don't display well, or render in confusing ways: \u0001 - \u001F and \u007F - \u009F.
3. The following characters are not allowed: \ud800 - uF8FF, \uFFF0 - uFFFF.
4. The "." character can be used as part of another name, but "." and ".." cannot alone be used to indicate a node along a path, because ZooKeeper doesn't use relative paths. The following would be invalid: "/a/b/./c" or "/a/b/../c".
5. The token "zookeeper" is reserved.

### ZNodes (数据节点)

ZooKeeper树中的每个节点都称为znode. Znodes主要由一个包括数据更改的版本号，acl更改stat数据结构组成。同时stat结构也有时间戳。 版本号和时间戳一起允许ZooKeeper验证缓存并协调更新。 每次znode的数据更改时，版本号增加。 例如，每当客户端检索数据时，它也接收数据的版本。 
当客户端执行更新或删除时，它必须同时提供它想要改变的znode数据的版本。 如果它提供的版本与实际版本的数据不匹配，更新将失败。
1. Watches: 客户端可以再Znode上设置监听器, 数据的改变会触发监听, 然后通知给客户端, 再清空对应的监听器;
2. Data Access: 存储在命名空间中每个znode的数据以原子方式读取和写入。 读取znode上的所有数据字节，写入替换所有数据。 每个节点都有一个访问控制列表（ACL），限制谁可以做什么。记住Znode的设计不是用来存在大量数据的, 虽然原则上可以支持到1M,
但是由于节点数据较大, 会在存储与同步的过程中耗时较多, 这样就造成了其他节点读取的延迟性, 所以推荐znode中只存储配置类信息, 一定要用与大数据环境可以考虑将数据存储到Hdfs等系统中,然后只把文件的索引存储到znode中的方案;
3. Ephemeral Nodes: ZooKeeper也有临时节点的概念。 只要创建znode的会话处于活动状态，这些znode就会存在。 当会话结束时，znode被删除。 因为这种行为，临时znode不允许有孩子。
4. Sequence Nodes -- Unique Naming: 当创建znode时，你也可以请求ZooKeeper在路径的末尾添加一个单调增加的计数器。 此计数器对父znode是唯一的。 计数器具有％010d的格式，即具有0（零）填充的10位数（计数器以这种方式格式化以简化排序），即“<path> 0000000001”。 
 注意：用于存储下一个序列号的计数器是由父节点维护的有符号int（4字节），当超过2147483647（导致名称“<path> -2147483647”）时，计数器将溢出。
5. Container Nodes (Added in 3.6.0): ZooKeeper有容器节点的概念。 容器节点是对于诸如领导，锁等等的配方有用的特殊目的节点。当容器的最后一个子节点被删除时，容器将成为服务器在未来某个时刻删除的候选节点。

### Time in ZooKeeper (时间)

ZooKeeper 有以下几种追踪时间的方式:
1. Zxid: 每次对ZooKeeper状态的更改都会以zxid（ZooKeeper事务标识）的形式接收邮票。 这暴露了对ZooKeeper的所有更改的总排序。 每个更改将有一个唯一的zxid，如果zxid1小于zxid2，则zxid1发生在zxid2之前。
2. Version numbers: 对节点的每个更改都将导致该节点的某个版本号增加。 三个版本号是版本（znode数据的更改次数），cversion（znode的子节点的更改次数）和aversion（znode的ACL的更改次数）。
3. Ticks: 当使用多服务器ZooKeeper时，服务器使用时间间隔来定义事件的时间，例如状态上传，会话超时，对等体之间的连接超时等。通过最小会话超时（2倍的时间间隔）间接暴露滴答时间; 如果客户端请求会话超时小于最小会话超时，则服务器将告诉客户端会话超时实际上是最小会话超时间间隔。
4. Real time: ZooKeeper不使用实时或时钟时间，除了在znode创建和znode修改上将时间戳放入stat结构。

### ZooKeeper Stat Structure (状态结构)

ZooKeeper中每个znode的Stat结构由以下字段组成:
1. czxid:
2. mzxid:
3. pzxid:
4. ctime:
5. mtime:
6. version:
7. cversion:
8. aversion:
9. ephemeralOwner:
10. dataLength:
11. numChildren:

### ZooKeeper Sessions (缓存)

Client通过与zkserver创建一个handle从而建立会话, 刚刚创建handle时它的stat是CONNECTING, 然后client lib试图去与zkserver 列队中的一个, 一旦连接成功, handle的stat就变为CONNECTED. 如果发生了不可恢复的错误, 比如会话过期, 授权失败或者应用程序关闭了handle等,
这时handle的状态会变为CLOSED;
版本3.2.0时增加了client与zkserver创建连接时可以指定工作目录比如 "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a", 这样的好处可以运行多个不相关应用服务于同一套zkserver上面, 方便管理而不受影响;
client与zkserver创建连接时, server会返回一个64-bite大小的sessionid和一个password, 当该client试图连接zk集群中的其他server时, 可以带上这些参数以便让server校验;
zk支持动态的改变zkserver集群的服务器个数, 变多或者变少都有可能导致部分client的连接进行断开当前连接重新创建新连接事件的发生以便达到负载均衡的目的;
client与server之间会设置有超时时间, 两者可以以心跳的方式可以维持会话, 这样一是为了让server知道client还没有关闭, 同时也是让client知道它所连接的server还活着;

### ZooKeeper Watches (监听者)
zk里面总共有三个可用的事件调用方式, getData(), getChildren(), and exists()
为一个znode设置一个watch, 当对应事件发生时, 会对client进行通知同时会删除watch, 如果还需要对该znode进行监听需要添加新的watch;
watch的通知是异步, 就是说可能通知还没到到达监听者那里但是数据改变的发起者已经得到了成功回应;

### Semantics of Watches ()

三种设置事件监听方式与数据改变的对应关系
* Created event:
Enabled with a call to exists.
* Deleted event:
Enabled with a call to exists, getData, and getChildren.
* Changed event:
Enabled with a call to exists and getData.
* Child event:
Enabled with a call to getChildren.

### Remove Watches ()

我们可以移除节点上的watch
* Child Remove event:
Watcher which was added with a call to getChildren.
* Data Remove event:
Watcher which was added with a call to exists or getData.

### What ZooKeeper Guarantees about Watches ()

* zk会对watch的响应及其他watch, 异步的响应进行排序, zk client会保证这种顺序;
* zk可以保证一个client收到数据改变的事件一定在它看到数据改变之前, 并且不同的数据改变一定是按照顺序的被client收到;
* ZooKeeper监视事件的顺序对应于ZooKeeper服务所看到的更新顺序。

### Things to Remember about Watches ()

### ZooKeeper access control using ACLs ()

### ACL Permissions ()

ZooKeeper supports the following permissions:
* CREATE: you can create a child node
* READ: you can get data from a node and list its children.
* WRITE: you can set data for a node
* DELETE: you can delete a child node
* ADMIN: you can set permissions

### Pluggable ZooKeeper authentication ()

### Consistency Guarantees ()

* Sequential Consistency: Updates from a client will be applied in the order that they were sent.
* Atomicity: Updates either succeed or fail -- there are no partial results.
* Single System Image: A client will see the same view of the service regardless of the server that it connects to.
* Reliability: Once an update has been applied, it will persist from that time forward until a client overwrites the update.
* Timeliness: 

### Bindings

当一个zookeeper对象呗创建的时候同时会创建两个线程: 一个IO线程和一个event线程
* IO线程: All IO happens on the IO thread (using Java NIO)
Session maintenance such as reconnecting to ZooKeeper servers and maintaining heartbeat is done on the IO thread. Responses for synchronous methods are also processed in the IO thread.
* Event线程: All event callbacks happen on the event thread
All responses to asynchronous methods and watch events are processed on the event thread.




=======
# Technology

*** Just do some test for amusing things. By the way, I had reserved example code to review for guys in the future!
>>>>>>> Stashed changes
