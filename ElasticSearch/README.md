# ElasticSearch 
Elasticsearch (以下简称es)是一个分布式、可扩展、实时的搜索与数据分析引擎。基于Lucene, 可以用来进行全文检索和结构化数据的实时统计分析;

## 环境搭建

### 搭建es集群(这里配置三节点, 3主片/3副片的环境)
1. Win10, jdk8, es5.6.2, kibana5.6.3(可视化操作es),sense-2.0.0-beta7(一个 Kibana 应用 它提供交互式的控制台，通过你的浏览器直接向 Elasticsearch 提交请求) 
2. 下载 [ES](https://www.elastic.co/cn/products/elasticsearch), [kibana](https://www.elastic.co/cn/products/kibana), 尽量保持两者版本一致
3. 新建文件夹 _es-cluster_, 将es的压缩包copy到该文件夹, 并解压缩出来三份, 分别命名为 **elasticsearch-5.6.2-9201, elasticsearch-5.6.2-9202, elasticsearch-5.6.2-9203**, 
再创建数据文件夹(../es-cluster/data) 日志存储文件夹(../es-cluster/log)
4. 修改es节点服务中的配置文件(../es-cluster/elasticsearch-5.6.2-9201/config/elasticsearch.yml), 示例参考(src/resources/config/es/*)
5. 启动节点9201, 执行(../es-cluster/elasticsearch-5.6.2-9201/bin/elasticsearch.bat), 访问服务 [es-health](http://127.0.0.1:9201/_cluster/health), 这里显示服务状态为**red**,
表示不是所有的主分片可用, 当前机器还不能正常工作, 因为我们在配置文件中配置的至少上线2个节点, 才会进行master选举
6. 启动节点9202, 执行(../es-cluster/elasticsearch-5.6.2-9202/bin/elasticsearch.bat), 再次访问服务[es-health](http://127.0.0.1:9201/_cluster/health), 发现服务状态已经变为**green**,
表示所有主片和副片都可用, 机器可以正常工作
6. 将kibana解压至 _es-cluster/kibana-5.6.2-windows-x86_, 修改配置 _kibana-5.6.2-windows-x86/config/kibana.yml_ 中的 **elasticsearch.url: "http://localhost:9201"**,
示例参考(src/resources/config/kibana/*)
6.1.(可忽略) 安装sense, `_bin\kibana-plugin.bat install elastic/sense_`, 如果安装失败请直接下载[sense](https://download.elastic.co/elasticsearch/sense/sense-2.0.0-beta7.tar.gz), 然后再
`_bin\kibana-plugin.bat install -d "" sense`
7. 启动kibana, 执行(../es-cluster/kibana-5.6.2-windows-x86/bin/kibana.bat), 访问 [kibana-service](http://127.0.0.1:5601/app/kibana)
8. 添加一个名为'blogs'的index, 并为它分配三个分片并每个分片配属一个副片, 打开kibana面板后, 在 _Dev Tools_ 菜单中执行 
`curl -XPUT 'localhost:9200/blogs?pretty' -H 'Content-Type: application/json' -d'
 {
    "settings" : {
       "number_of_shards" : 3,
       "number_of_replicas" : 1
    }
 }
 '
`
9. 再次访问ES服务查看状态 [status](http://127.0.0.1:9201/_cluster/health), 会看到有对应的分片状态;



