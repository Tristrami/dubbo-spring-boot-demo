# Dubbo Demo

## 1. 一个简单的 demo

> 这个简单的 demo 将使用 dubbo 3，基于 spring boot 进行构建

整体项目结构

```shell
├── dubbo-spring-boot-demo-consumer
├── dubbo-spring-boot-demo-interface
├── dubbo-spring-boot-starter-provider
├── pom.xml
└── readme.md
```

接口端用于定义所有的接口，服务生产者提供接口的实现类，服务消费者调用接口

### 1.1 服务接口端

定义一个 `GreetingService` 接口

```java
public interface GreetingService
{
    String greeting(String name);
}
```

### 1.2 服务生产者

依赖

```xml
<dependencies>

    <dependency>
        <groupId>com.seamew</groupId>
        <artifactId>dubbo-spring-boot-demo-interface</artifactId>
    </dependency>

    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-dependencies-zookeeper-curator5</artifactId>
        <type>pom</type>
        <exclusions>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-reload4j</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

</dependencies>
```

配置文件

```properties
server.port=8000

dubbo.application.name=dubbo-spring-boot-demo-provider

# 协议配置
dubbo.protocol.name=dubbo
dubbo.protocol.port=-1

# 注册中心配置
dubbo.registries.ningbo.address=zookeeper://${zookeeper.address:127.0.0.1}:${zookeeper.port:2181}
```

编写 `GreetingSerivce` 的实现类，并使用 `@DubboSerivce` 注解来注册服务

```java
@DubboService
public class GreetingServiceImpl implements GreetingService
{
    @Override
    public String greeting(String name)
    {
        return "Hola, " + name;
    }
}
```

### 1.3 服务消费者

依赖

```xml
<dependencies>

    <dependency>
        <groupId>com.seamew</groupId>
        <artifactId>dubbo-spring-boot-demo-interface</artifactId>
    </dependency>

    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-dependencies-zookeeper-curator5</artifactId>
        <type>pom</type>
        <exclusions>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-reload4j</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

</dependencies>
```

配置文件

```properties
server.port=8010

dubbo.application.name=dubbo-spring-boot-demo-provider

# 协议配置
dubbo.protocol.name=dubbo
dubbo.protocol.port=-1

# 注册中心配置
dubbo.registries.ningbo.address=zookeeper://${zookeeper.address:127.0.0.1}:${zookeeper.port:2181}
```

编写 dubbo 服务的配置类 `ReferencingConfig`，将 dubbo 生成的代理对象放入 spring 容器

```java
@Configuration
public class ReferencingConfig
{
    @Bean
    @DubboReference(interfaceClass = GreetingService.class)
    public ReferenceBean<GreetingService> greetingService()
    {
        return new ReferenceBean<>();
    }
}
```

编写 `GreetingTask`，注入 `GreetingService` 的实现类，并实现 `CommandLineRunner` 接口，在容器启动时调用 `greeting` 方法

```java
@Component
@Slf4j
public class GreetingTask implements CommandLineRunner
{
    @Autowired
    private GreetingService greetingService;

    @Override
    public void run(String... args) throws Exception
    {
        new Thread(() -> {

            try {
                while (true) {
                    String greeting = greetingService.greeting("seamew");
                    log.info("Greeting! {}", greeting);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log.error("", e);
                Thread.currentThread().interrupt();
            }

        }, "greeting").start();
    }
}
```

上面的 `ReferencingConfig` 也可以省略，在 `GreetingTask` 中可以使用 `DubboReference` 直接注入 `GreetingService` 的实现，但是官方更加推荐前面一种方法

### 1.4 接口版本号

在服务端可以通过 `@DubboService` 中的 `version` 属性指定实现类的版本

```java
@DubboService(version = "chinese")
public class GreetingServiceImplV2 implements GreetingService
{
    @Override
    public String greeting(String name)
    {
        return "你好，" + name;
    }
}
```

在消费者端指定 `@DubboReference` 的 `version` 属性，即可获取对应版本的实现类

```java
@Bean(name = "greetingServiceV2")
@DubboReference(interfaceClass = GreetingService.class, version = "chinese")
public ReferenceBean<GreetingService> greetingServiceV2()
{
    return new ReferenceBean<>();
}
```

## 2. Dubbo 多注册中心支持


## 3. Dubbo 多协议支持

http

依赖

resteasy-jaxrs

resteasy-client

jetty-server

## 4. Dubbo 负载均衡

random 加权随机，由区间算法实现

roundrobin 加权轮询

一致性哈希算法

最小活跃度，性能高权重高，由计数器来衡量性能，接收到请求计数器 + 1，处理完请求计数器 - 1，计数器值越小性能越高

最短响应时间

标签路由及条件路由

## 7. Dubbo 常见配置

启动时检查

```properties
# 启动时检查注册中心
dubbo.registries.ningbo.check=true
```

主机绑定

 