# Dubbo+springboot+nacos+zipKin 示例项目

> Apache Dubbo  |ˈdʌbəʊ| 是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。

### 所有pom依赖配置见具体项目

### springboot+dubbo github地址

https://github.com/apache/dubbo-spring-boot-project

## 快速开始

首先，我们假设存在一个 Dubbo RPC API ，由服务提供方为服务消费方暴露接口 :

```java
public interface DemoService {

    String sayHello(String name);

}
```

### 实现 Dubbo 服务提供方

1. 实现 `DemoService` 接口

    ```java
    @Service(version = "1.0.0")
    public class DefaultDemoService implements DemoService {
    
        /**
         * The default value of ${dubbo.application.name} is ${spring.application.name}
         */
        @Value("${dubbo.application.name}")
        private String serviceName;
    
        public String sayHello(String name) {
            return String.format("[%s] : Hello, %s", serviceName, name);
        }
    }
    ```



2. 编写 Spring Boot 引导程序

    ```java
    @EnableAutoConfiguration
    public class DubboProviderDemo {

        public static void main(String[] args) {
            SpringApplication.run(DubboProviderDemo.class,args);
        }
    }
    ```


3. 配置 `application.properties` :

    ```properties
    # Spring boot application
    spring.application.name=dubbo-auto-configuration-provider-demo
    # Base packages to scan Dubbo Component: @org.apache.dubbo.config.annotation.Service
    dubbo.scan.base-packages=org.apache.dubbo.spring.boot.sample.provider.service

    # Dubbo Application
    ## The default value of dubbo.application.name is ${spring.application.name}
    ## dubbo.application.name=${spring.application.name}

    # Dubbo Protocol
    dubbo.protocol.name=dubbo
    dubbo.protocol.port=12345

    ## Dubbo Registry
    dubbo.registry.address=N/A
    ```

### 实现 Dubbo 服务消费方


1. 通过 `@Reference` 注入 `DemoService` :

    ```java
    @EnableAutoConfiguration
    public class DubboAutoConfigurationConsumerBootstrap {
    
        private final Logger logger = LoggerFactory.getLogger(getClass());
    
        @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
        private DemoService demoService;
    
        public static void main(String[] args) {
            SpringApplication.run(DubboAutoConfigurationConsumerBootstrap.class).close();
        }
    
        @Bean
        public ApplicationRunner runner() {
            return args -> {
                logger.info(demoService.sayHello("mercyblitz"));
            };
        }
    }
    ```



2. 配置 `application.yml` :

    ```yaml
    spring:
      application:
        name: dubbo-auto-configure-consumer-sample
    ```


确保 Dubbo 服务提供方服务可用， `DubboProviderDemo` 运行方可正常。


## nacos （配置中心+注册中心）
### 1.安装方式（两种方式）
1.从github上下载源码方式
```shell script
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
ls -al distribution/target/

// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin
```
2.下载编译后压缩包方式
```shell script
unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
cd nacos/bin
```
#### Linux
启动命令(standalone代表着单机模式运行，非集群模式):
```shell script
sh startup.sh -m standalone
```
#### Windows
```shell script
cmd startup.cmd
```

## zipKin(链路追踪)
### 安装方式
建议通过阿里云仓库下载zipKin jar包（官网下载很慢）

地址：https://maven.aliyun.com/mvn/search 搜索zipkin-server，选择exec.jar后缀的下载

### 启动方式
jar包路径下执行：

java -jar zipkin-server-x.xx.x（版本号,本例选择版本为2.21.4）-exec.jar 

服务启动后默认可以通过9411端口访问zipkin的监控页面

此启动方式采用默认的In-Memory方式存储，无法持久化。线上环境需搭配MySql或Cassandra或Elasticsearch进行持久化，还可加入mq增加吞吐量



