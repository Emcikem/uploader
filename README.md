# uploader

前端 vue
后端 springboot

## 前端
使用vue-simple-uploader作为文件上传的组件。前后端调用是axios

## 后端

开发框架springboot

数据存储 MySQL

数据缓存 Redis

中间件 canal，负责同步MySQL和elasticsearch的数据

数据搜索 elasticsearch


特点
1. 分片上传，断点续传，秒传，支持大文件上传。
2. 支持配置功能，配置存储方式：本地存储，OSS存储，用策略模式，代码可扩展。配置存储位置，是否分片存储。
3. 搜索功能，采用es的倒排索引，对于海量文件时的搜索功能。
4. 分布式，可以多机部署，目前是部署在docker上
5. 定时任务quarz清理文件随便，防止分片产生的垃圾文件，以及副本的数据检查

用HDFS的思想，去实现文件的存储

TODO：
- [ ] 一致性hash，用于DataNode的选择
- [ ] NameNode和SecondaryNode的实现
- [ ] DataNode心跳检测以及副本的赋值
- [ ] 副本的数据进行校验，通过副本进行数据对照分析，数据块的校验码
- [ ] 冗余因子的设置，以及分片大小策略调整，分片改成64MB


## 配置与部署

### 后端
在docker上创建一个uploader文件夹
```
mkdri uploader
```
把后端jar放到这个文件夹，再创建一个Dockerfile
```
FROM java:8
VOLUME /tmp
ADD file-uploader.jar file-uploader.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar", "/file-uploader.jar"]
```
运行
```
ocker build -t file-uploader .
docker run -d -p 8989:8989 --name file-uploader-8989 file-uploader
```
接着把8989端口设置开放
### 前端
在uploader里面创建一个文件夹dist，把dist.zip放进去，然后解压
```
unzip dist.zip
```
创建一个Dockerfile
```
FROM nginx:latest
COPY ./dist /usr/share/nginx/html
EXPOSE 80
```
运行
```
docker build -t file-ui .
docker run -d -p 80:80 --name file-ui--80 file-ui
```
开放80端口
接着，浏览器输入公网ip即可访问前端

### canal
有server和adapter两个部分
```
docker pull canal/canal-server:latest
docker run --name canal115 -p 11111:11111 -d canal/canal-server:v1.1.5
```
配置
```
server:
  port: 8081
spring:
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
    default-property-inclusion: non_null

canal.conf:
  mode: tcp #tcp kafka rocketMQ rabbitMQ
  flatMessage: true
  zookeeperHosts:
  syncBatchSize: 1000
  retries: 0
  timeout:
  accessKey:
  secretKey:
  consumerProperties:
    # canal tcp consumer
    canal.tcp.server.host: 你的服务器地址:11111
    canal.tcp.zookeeper.hosts:
    canal.tcp.batch.size: 500
    canal.tcp.username:
    canal.tcp.password:

  srcDataSources:
    defaultDS:
      url: jdbc:mysql://数据库地址:3306/file_uploader?useUnicode=true
      username: root
      password: admin
  canalAdapters:
  - instance: example # canal instance Name or mq topic name
    groups:
    - groupId: g1
      outerAdapters:
      - name: logger
      - name: es7
        hosts: es的地址:9200 # 127.0.0.1:9200 for rest mode
        properties:
          mode: rest
          cluster.name: file-uploader-es

```

```
docker pull slpcat/canal-adapter:v1.1.5
docker run --name adapter115 -p 8081:8081 -d slpcat/canal-adapter:v1.1.5
```

配置
```
select 
f.id as _id, f.file_name as fileName, f.identifier as identifier, f.total_size as totalSize, f.update_time as updateTime
from 
tbl_file f
```


### elasticsearch
配置
```
cluster.name: file-uploader-es
network.host:0.0.0.0

node.name: node-1
http.port: 9200
http.cors.enabled: true
http.cors.allow-origin: "*"
node.master: true
node.data: true
```


### MySQL
```
docker pull mysql:5.7.36
docker run --name mysql5736 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin -d mysql:5.7.36
```
开放3306端口
### Redis
```
docker pull redis:latest
docker run -itd --name redis -p 6379:6379 redis
```
开放6379端口
