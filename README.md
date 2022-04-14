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
4. 分布式，可以多机部署。
