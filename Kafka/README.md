# Kafka
Kafka用于构建实时数据管道和流应用程序。它是水平可扩展，容错，恶性快，并在成千上万的公司中使用。

## Apache Kafka是一个分布式流数据处理平台, 具体是什么意思呢？

### 我们通常所认为的流处理平台应该主要有以下三种能力
1. 允许发布与订阅流数据; 这一点可能会和消息队列或者以下企业的消息系统有点类似.
2. 可以容错性的存储流数据.
3. 能够让以流数据产生的顺序进而去处理他们;


## Kafka 有那些优势呢

### 它主要应用于以下两类应用程序场景
1. 构建一种实时的, 可靠的, 介于系统或应用程序之间的流数据获取管道;
2. 构建对数据流进行变换或反应的实时流应用程序

为了更清楚的了解Kafka做了哪些事情, 下面就让我们一起进入了解Kafka的能力..

### 首先是几点概念
1. Kafka作为群集在一个或多个服务器上运行。
2. Kafka集群以称为主题的类别存储记录流。
3. 每个记录由一个键，一个值和一个时间戳组成。

### Kafka的四大核心API
1. Producer: 
2. Consumer:
3. Streams:
4. Connector:

![kafka-apis](https://cloud.githubusercontent.com/assets/5526657/23831359/9ceaea3e-075a-11e7-97ac-232c4c24ffef.png)

### linux kafka 服务启停
1. 先修改zk的配置文件，修改 **_`config/zookeeper.properties`_** 文件中的zk服务端口和zk持久化文件存储目录
2. 先启动zkserver: **_cd ${root-zkserver}; bin/zkServer.sh start-foreground_**; (以前端模式启动方便观看控制台输出, 默认zk服务端口为2181)
3. 启动单体的kafka服务: **_cd ${root-kafka}; bin/kafka-server-start.sh config/server.properties_**;
4. 创建一个kafka的topic: **_bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test_**
5. 连接topic,Producer发送消息: **_bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test_**
6. 启动一个customer, 进行消息的消费: **_bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning_**

### windows kafka 服务启停
1. 先修改zk的配置文件，修改 **_`config/zookeeper.properties`_** 文件中的zk服务端口和zk持久化文件存储目录
2. 启动zk，执行 **_.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties_**
3. 启动单体的kafka服务, 指定的zk端口需要和上面第一步中设置的一致: **_.\bin\windows\kafka-server-start.bat .\config\server.properties_**
4. 创建一个kafka的topic: **_bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test_**
5. 连接topic,Producer发送消息: **_bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test_**
6. 启动一个customer, 进行消息的消费: **_bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning_**

