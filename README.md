# RS232-client

## 介绍
基于Java 17和Java FX技术搭建的RS232串口通信客户端，已经实现了电子秤的数据通信、数据解析及数据输出。

## 技术架构
JDK 17 + Java FX + JPMS

## 依赖
1. JDK: graalvm-jdk-17.0.10
2. Java FX: org.openjfx(17.0.6)
3. jSerialComm: 2.10.4

## 使用说明
### 1. 直接启动
启动类[RS232ClientApplication.java](src/main/java/pers/hll/rs232/rs232client/RS232ClientApplication.java)

### 2. 应用程序 
#### 2.1. 构建运行时镜像 ```mvn javafx:jlink```
构建完成的[运行时镜像目录](./target/app)
#### 2.2. 构建安装包
构建 dmg (macOS的应用程序安装包)
[jpackage命令说明文档](https://docs.oracle.com/en/java/javase/17/docs/specs/man/jpackage.html)
```shell
jpackage \
  --type dmg \
  --app-version 1.0.0 \
  --copyright "Copyright (c) 2024 RS232 Client" \
  --description "RS232 Client" \
  --name RS232Client \
  --input ./target \
  --main-jar rs232-client-1.0-SNAPSHOT.jar \
  --main-class pers.hll.rs232.rs232client.RS232ClientApplication \
  --dest out \
  --runtime-image ./target/app/ 
```
构建完成的[RS232Client-1.0.0.dmg](./out/)文件, 双击安装, 在弹出页面里将RS232Client.app拖至Applications目录下即可.


#### 参与贡献
1. Fork / Star 本仓库
2. 提交代码 (注释/bug修复/新功能/优化)
3. 提交 Issue.