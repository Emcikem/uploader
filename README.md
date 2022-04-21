# uploader

前端 vue
后端 springboot

## 前端
使用vue-simple-uploader作为文件上传的组件。前后端调用是axios

## 后端

开发框架springboot
数据存储 MySQL数据持久哈
数据缓存 Redis
中间件 canal，负责同步MySQL和elasticsearch的数据
数据搜索 elasticsearch


特点
1. 分片上传，断点续传，秒传，支持大文件上传。
2. 支持配置功能，配置存储方式：本地存储，OSS存储，用策略模式，代码可扩展。配置存储位置，是否分片存储。
3. 搜索功能，采用es的倒排索引，对于海量文件时的搜索功能。
4. 分布式，可以多机部署，目前是部署在docker上
5. 定时任务quarz清理文件随便，防止分片产生的垃圾文件


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

### elasticsearch
