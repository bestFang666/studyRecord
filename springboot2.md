# SpringBoot2 基础篇

## 命名规范

* 类的名称UpperCamel

* 包的名称lowCamel

## 环境要求

* Java 8 - 14 以上的环境（Spring5 基于Java 8 实现）

* Spring Framework 5.2.9.RELEASE+

* Maven 3.3+

## 预备知识

* Spring 生态圈
  
  参考网址： spring.io

* SpringBoot
  
  * 简化了”配置地狱“问题，专注业务代码即可
  
  * 底层是Spring框架，整合了Spring整个系列技术栈 
  
  * 参照 SpringBoot 官方文档进行学习（project -> learn -> 最新版本 [GA] ）
  
  * 查看框架的变化： https://github.com/spring-projects/spring-boot/wiki#release-notes  （尤其要注意大版本的变化 [1.0->2.0] 要看整个框架的变化，中版本 [1.1->1.2] 看开发文档即可，小版本 [1.1.1->1.1.2] 无需关心）

## 配置

* Maven：
  
  在修改 conf 中 setting.xml 文件，加入如下的 jdk 版本和镜像配置
  
  ```xml
  <mirrors>
        <mirror>
          <id>nexus-aliyun</id>
          <mirrorOf>central</mirrorOf>
          <name>Nexus aliyun</name>
          <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>
  
    <profiles>
           <profile>
                <id>jdk-1.8</id>
                <activation>
                  <activeByDefault>true</activeByDefault>
                  <jdk>1.8</jdk>
                </activation>
                <properties>
                  <maven.compiler.source>1.8</maven.compiler.source>
                  <maven.compiler.target>1.8</maven.compiler.target>
                  <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
                </properties>
           </profile>
    </profiles>
  ```

## 入门

* **相关注解（一般可以理解向容器中注册组件）**
  
  * @SpringBootApplication： 表示一个 SpringBoot 应用
    
    * @SpringBootConfiguration  
      @EnableAutoConfiguration  
      @ComponentScan（”包名“）
      
      一个封装了该三个
  
  * @RestController： 合并 @Controller 和 @ResponseBody
    
    * @ Controller： web 层
    
    * @ResponseBody： 将数据写回给浏览器

* **引入依赖**
  
  ```xml
  <!--    使用 SpringBoot 功能需要引入 SpringBoot 父项目依赖-->
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>2.7.2</version>
      </parent>
  <!--    引入 web 场景来开发 web -->
  <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
  ```
- **SpringBoot 简化了配置**

- 在 resources 中新建一个 application.properties/.yml 文件来<u>**全局管理**</u>所有配置
  
  - 修改 tomcat 配置（比如：端口号）
  
  - 修改 SpringMVC 配置（web 上）
  
  - 参照官方文档： https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties

- **运行**

- 编写一个主程序

- 直接运行注解了 @SpringBootApplication 类下的 main 函数（主程序）即可

- **部署**

- 在 pom.xml 添加插件

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

* 打包成 jar 包
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-16-14-10-39-image.png)

* 打开命令行运行该 jar 包
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-16-14-11-41-image.png)
  
  ```cmd
  # 使用命令行运行
  java -jar springboot2-1.0-SNAPSHOT.jar
  ```

## 1. SpringBoot 的特点

### 1.1 依赖管理

* 每个 SpringBoot 都有一个父工程
  
  ```xml
  <!--    使用 SpringBoot 功能需要引入 SpringBoot 父项目依赖-->
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>2.7.2</version>
      </parent> 
  
  <!-- 父项目的父项目-->
   <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>2.7.2</version>
    </parent>
  ```
  
  作用：
  
  * 用父项目来做依赖管理
    
    * 子项目继承了父项目，使用其他依赖就不需要版本号 (比如 web 依赖<u>就不需要版本号</u>)，因为<u>父项目几乎声明了所有开发中常用的依赖版本号</u>，即自动版本仲裁机制（已经有了默认版本号）
    
    * 如果想自定义版本号，在 pom.xml 中添加如下来修改重写
  
  ```xml
  <!-- 修改自己想要的版本(每个依赖都有对应属性[查看 spring-boot-dependencies 
  中]，此处为 mysql.version)-->
  <properties>
          <mysql.version>5.1.43</mysql.version>
   </properties>
  ```
  
  * starter 场景启动器
    
    * 例如：spring-boot-starter-*，*代表场景，只要引入该 starter，相关场景的依赖都会自动引入
    
    * 所支持的 starter:  https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters
  
  ```xml
  <!-- 所有场景启动器最底层的依赖 -->
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <version>2.7.2</version>
        <scope>compile</scope>
   </dependency>
  ```

### 1.2 自动配置

* 自动配置好 Tomcat
  
  * 引入了 Tomcat 依赖
  
  * 配置了 Tomcat

* 自动配好了SpringMVC
  
  * 引入了 SpringMVC 全套组件
  
  * 自动配好 SpringMVC 常用组件（功能）
    
    * 字符编码问题
    
    * 拦截器
    
    * 视图解析器
    
    * SpringBoot 帮我们配置好了所有 web 开发的常见场景
  
  ```java
  //打印 spring MVC 相关组件
  ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
   String[] names = run.getBeanDefinitionNames();
   for (String name : names) {
       System.out.println(name);
   }
  ```

* 默认的包结构
  
  * 参考链接： https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code
  
  * 主程序<u>所在的包及其子包</u>里面所有的组件都会被默认扫描，之外的不会被扫描
    
    * 无需以前包扫描
    
    * 如果需要修改包扫描，可以在注解添加属性@SpringBootApplication(scanBasePackage="包名")
      
      或者使用 @ComponentScan 制定扫描路径

* 各种配置（application.properties文件当中内容）拥有默认值
  
  * 默认配置最终都是映射到某一个类上
  
  * 配置文件的值最终会绑定某个类上，这个类在容器中会创建对象（对象有默认值）

* 按需加载所有自动配置项
  
  * starter 引入了哪个场景就加载哪个场景，相关配置才会自动开启
  
  * SpringBoot 所有自动配置功能，包含所有场景（点开IDEA显示红色就是未被导入）
  
  ```xml
      <!--在springboot-starter中 -->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
        <version>2.7.2</version>
        <scope>compile</scope>
      </dependency>
  ```

## 2. 容器功能

### 2.1 组件添加

#### 2.1.1 @Configuration

* 对比以前的 Spring，在容器中注册组件，需要创建配置文件，使用 bean 标签

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-16-16-43-03-image.png)

* SpringBoot 使用 @Configuration 和 @Bean 注解，使用 @Bean 标注来向容器中注册组件，是<u>**单实例**</u>
  
  * <u>配置类（带 @Configuration 注解）本身就是组件</u>
  
  * @SpringBootApplication： 标识主配置类
  
  * @Configuration 
    
    * proxyBeanMethods = true，默认就是 true，<u>**单实例的保证**</u>，**<u>组件依赖模式</u>** ------ Full 模式
    
    * proxyBeanMethods = false 不用每次都去容器里面找，直接创建即可，不保证单实例 ------ Lite模式
    
    * **<u>Full/Lite 模式（SpringBoot2 特有）</u>**
  
  * 外部无论对配置类中的组件注册方方调用多少次，仍是之前注册在容器中的单实例对象，原因在于 proxyBeanMethods = true

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-10-35-50-image.png)

#### 2.1.2 @Component/@Controller/@Service/@Repository

**继承原来 Spring 老一套**

* @Component： 类上标注表示该类是一个组件

* @Controller： 类上标注表示该类是一个 web 控制器组件

* @Service： 类上标注表示该类是一个业务逻辑组件

* @Repository： 类上标注表示该类是一个数据库层的组件

#### 2.1.3 @Import

* 此注解<u>**写在组件上**</u>

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-09-38-57-image.png)

* @ import({A.class, B.class, ...})：给容器中自动创建 A, B 这两个类型的组件, 默认组件的名字是<u>**全类名**</u>（com.fang.XXX.xxx）， 与 @Bean 区别开，@Bean 注册的组件是<u>**方法名**</u>

#### 2.1.4 @Conditional

* 条件装配：满足 Conditional 指定的条件，进行组件注入（注册）
  
  <u>**常用**</u>
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-09-48-34-image.png)

* ConditionalOnXXX 对 XXX 生效
  
  例如：如果容器中没有 tomcat 组件，那么”你好啊“组件就不会自动注入容器（此处<u>**不存在“你好啊”组件**</u>，原因是向容器中注入组件是按照代码顺序逻辑，“你好啊”注入时候“tomcat”还没注入）

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-10-06-51-image.png)

### 2.2 原生配置文件引入

#### 2.2.1 @ImportResource

* 在<u>**随便一个类上**</u>加入该注解既可以将配置文件（eg. beans.xml）中的组件注入容器当中

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-10-55-32-image.png)

### 2.3 配置绑定

* 使用 Java 读取 properties 文件中的内容，然后<u>**把它封装到 JavaBean**</u> 中，以供使用

#### 2.3.1 @Component + @ConfigurationProperties

* @ConfigurationProperties(prefix = "") 是跟 .properties 配置文件绑定

* @Component 组件注入

* 读取的话一定要有 <u>**Getter**</u> 方法

* 在<u>**对应的类**</u>上标识

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-11-20-59-image.png)

#### 2.3.2 @EnableConfigurationProperties +  @ConfigurationProperties

* @EnableConfigurationProperties(A.class) 在<u>**配置类**</u>上标识

* @ConfigurationProperties 在<u>**对应的类**</u>上标识

* 开启配置绑定功能，并且组件自动注入到容器中

* 对于使用<u>**第三方类**</u>时，可以使用该方法，不再使用2.3.1中方法

## 3. 实践

### 3.1 最佳实践

* 引入场景依赖 ------ starter-xxx
  
  * 查看依赖，当前层级看不到，往上一层级看

* 查看自动配置了哪些，<u>**两种方法**</u>
  
  * 自己分析，引入了某个场景，对应的自动配置就生效了
  
  * 开启自动配置报告，在配置文件（.properties）添加 debug=true

* 是否要修改
  
  * 参照文档来修改配置项（改 .properties 文件中的内容）
  
  * 自己分析，看相关 xxx.properties 绑定了哪些
  
  * 自定义加入或者替换组件
  
  * 自定义器  XXXXXCustomizer

## 4. 开发小技巧

### 4.1 lombok

* 简化了 JavaBean 的开发，不需要给 pojo 写 getter&setter 方法和一些构造器方法

* 在 pom.xml 中导入依赖
  
  ```xml
  <!--        引入 lombok-->
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
          </dependency>
  ```

* 安装 lombok 插件

* 直接在 pojo 上面写上注解，不自动生成就自己写
  
  * @Data： 生成 getter/setter 方法
  
  * @ToString： 生成 ToString 方法
  
  * @AllArgsConstructor： 生成全参构造器
  
  * @NoArgsConstructor： 生成无参构造器
  
  * @EqualsAndHashCode： 生成 equals 和 hashcode 方法

* @Slf4j： 日志注解
  
  * 调用 log.info("打印xxx") ------ 打印日志

### 4.2 dev-tools

* 参考链接： https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-14-50-30-image.png)

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-14-51-00-image.png)

* 导入依赖
  
  ```xml
  <!-- 项目的热更新 -->
  <dependencies>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-devtools</artifactId>
          <optional>true</optional>
      </dependency>
  </dependencies>
  ```

* 重启项目
  
  ```md
  # 直接使用该命令重载
  Crtl + F9
  # 不用去按重启按钮（原来方式） 
  shift + F10
  ```

### 4.3 Spring Initailizr

* 项目初始化向导

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-16-08-23-image.png)

* 可以<u>**按需去勾选相关的场景**</u>，他会自动给你所给需求下载一个项目供开发者使用

* 创建之后，包含了**依赖**、项目的**目录结构**，创建了**主程序**
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-17-16-11-54-image.png)

# SpringBoot2 核心技术篇

## 1. 配置文件

* 如果有多个配置文件，按顺序优先选择第一个

### 1.1 properties

* 配置一个全局文件
  
  ```properties
  # 修改服务端口号
  server.port = 8080
  
  mycar.brand = BYD
  mycar.price = 100000
  
  # 可以查看那些自动配置类生效了
  debug = true
  ```

### 1.2 yaml

* 非常适合用来做<u>**以数据为中心**</u>的配置文件，<u>**轻量**</u>，只是与 .properties 写法不同

* 注意层级之间的对齐

* 字符串无需加引号，如果加上，单引号代表转义（即可以输出转义字符[ \n 输出 ’\n‘ ]），双引号代表不转义（转义字符按照原来输出[ \n 输出为换行]）
  
  ```yml/yaml
  # k 和 v 之间有空格
  # 字面量
  k: v
  
  # 对象/键值对
  # 行内写法
  k: {k1: v1, k2: v2, k3: v3}
  # 等价于
  k: 
     k1: v1
     k2: v2
     k3: v3
  
  # 数组集合（map 不是集合）
  # 行内写法
  k: [v1, v2, v3]
  # 等价于(- 和 v 有空格)
  k:
   - v1
   - v2
   - v3
  ```

* 配置提示
  
  * 配置文件中<u>**没有自定类的自动提示**</u>，加入下面依赖便于开发
  
  * 需要使用注解@ConfigurationProperties(prefix = "person")才能与配置文件进行绑定，下面依赖才生效
  
  ```xml
  <!--        自定义类与配置文件进行绑定-->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-configuration-processor</artifactId>
              <optional>true</optional>
          </dependency>
  <!-- 轻量打包 -->
          <plugins>
              <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                  <configuration>
                      <!--  除去非业务逻辑（比如：插件、代码识别 etc）的依赖，打包时候会忽略下面的配置-->
                      <excludes>
                          <exclude>
                              <groupId>org.springframework.boot</groupId>
                              <artifactId>spring-boot-configuration-processor</artifactId>
                          </exclude>
                      </excludes>
                  </configuration>
              </plugin>
          </plugins>
      </build>
  ```

## 2. web 开发

* 参考链接： https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-18-10-50-18-image.png)

### 2.1 静态资源规则与定制化

#### 2.1.1 静态资源目录

* 在类路径下（即 resources 文件下）`/static` (or `/public` or `/resources` or `/META-INF/resources`) 文件夹里面的动态资源都可以直接通过根路径来访问
  
  <u>**开发常放在 /static 目录下**</u>
  
  ```md
  localhost:8080/abc.jpg
  ```

* 原理
  
  默认是 /**
  
  * 请求顺序
  1. 找 Controller
  
  2. 找静态资源
  
  3. 404

#### 2.1.2 静态资源访问前缀 && 路径

* 默认是<u>**无前缀**</u>，直接通过 <u>/资源名</u> 来访问

* 加前缀
  
  访问静态资源路径变为： /sta/资源名 （此处参照下面代码模块）

* 配路径
  
  参照如下代码，开发时候<u>**一般不配置**</u>

```yaml
spring:
  mvc:
    # 配置静态资源前缀 (默认是 /**)
    # 这个前缀会影响欢迎页 index.html 和网页logo favicon.icon的显示
    static-path-pattern: /sta/**

  web:
    resources:
      # 默认是在 resources 目录下四种文件夹（表示形式为集合）
      # 修改过后所有的静态资源都放在 static-locations 中
      static-locations: [classpath:/aaa/]
```

#### 2.1.3 欢迎页 && 图标

![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-18-16-53-31-image.png)

* 注意： Favicon <u>**用法好像有问题，运行不出来**</u>

### 2.2 请求处理

#### 2.2.1 Rest 风格

* @RequestMapping(value = "/user",method = RequestMethod.GET/DELETE/PUT/POST)
  
  * <u>**GET**</u>： 获取/查询 ------ /getUser
  
  * DELETE： 删除 ------ /deleteUser
  
  * PUT： 修改 ------ /editUser
  
  * <u>**POST**</u>： 保存 ------ /saveUser

* 一般只使用注解@GetMapping（url）/ @PostMapping(url)
  
  * GetMapping： 获取/查询
  
  * PostMapping： 删除/修改/保存

#### 2.2.2 注解方式

* **@PathVariable("key") object / @PathVariable Map <String, String>**
  
  ```java
  @RestController
  public class TestController {
  
      @RequestMapping("/car/{id}")
      public Map<String,Object> test1(@PathVariable("id") String ID) {
          Map<String,Object> map =new HashMap<String,Object>();
          map.put("ID",ID);
          return map;
  
      }
  }
  ```
  
  ```md
  # 请求链接
  localhost:8080/car/123
  ```
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-19-10-08-53-image.png)
  
  <u>**详解**</u>：@PathVariable("id") String ID 
  
  * 读取请求链接上的 123  对应到 id 上面，然后赋值给 ID

* **@RequestHeader 获取请求头**
  
  * @RequestHeader("key") object / @RequestHeader Map<String, String>, MultiValueMap<String, String>, or HttpHeaders
    
    * key 是对应请求中的字段
      
      ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-19-12-08-46-image.png)

* **@RequestParam("key") object /  @RequestParam Map<String, String> or MultiValueMap<String, String>**
  
  ```java
  @RestController
  public class TestController {
  
      @RequestMapping("/car/{id}")
      public Map<String,Object> test1(@PathVariable("id") String ID,
                                      @RequestParam("name") String NAME) {
          Map<String,Object> map =new HashMap<String,Object>();
          map.put("ID", ID);
          map.put("NAME", NAME);
          return map;
  
      }
  }
  ```
  
  访问链接：
  
  ```md
  # 请求链接
  # ***标了什么参数就要设置什么参数，queryString 格式***
  localhost:8080/car/123?name=456
  ```
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-19-12-01-29-image.png)

* **@CookieValue("key") object** 获取 cookie 值
  
  * key 是 cookie 中的字段
  
  * **@CookieValue("key"） Cookie object** 也可封装为一个 cookie 对象
    
    ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-19-12-52-02-image.png)

* **@RequestBody Object object** 获取请求体[post]
  
  * 前端对应表单提交到后端
  
  * 使用 @PostMapping
  
  ```html
  <!--测试post请求, action 绑定后端请求链接-->
      <form action="/testPost" method="post">
          <p>
              <label>姓名：</label><input type="text" name="username" value="11" />
          </p>
          <p>
              <label>密码：</label><input type="password" name="password" />
          </p>
          <p>
              <input type="submit" name="" value="提交">
              <input type="reset" name="" value="重置">
          </p>
      </form>
  ```
  
  ```java
   @PostMapping("/testPost")
      public Map<String, Object> test2 (@RequestBody String content) {
          Map<String,Object> map =new HashMap<String,Object>();
          map.put("content", content);
          return map;
      }
  ```

* 结果展示，按照前端中 name 字段发送给后端
  
  ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-19-14-21-46-image.png)

* **@RequestAttribute** 获取request域属性
  
  * 用于页面转发，得到前一页面的请求数据
  
  ```java
  @Controller
  public class RequestController {
  
      @GetMapping("/origin")
      public String goToPage (HttpServletRequest request) {
          // 给请求中设置属性
          request.setAttribute("msg","跳转成功...");
          request.setAttribute("code", "200");
          // forward 表示跳转至那个请求
          return "forward:/success";
     /**
       * @ResponseBody 将前端传来的json格式的数据转为自己定义好的 javabean 对象
       * @param MSG
       * @param CODE
       * @return
       */
      @ResponseBody
      @GetMapping("/success")
      public Map<String, Object> endPage(@RequestAttribute("msg") String MSG,
                                         @RequestAttribute("code") Integer CODE){
          Map<String, Object> map =new HashMap<String, Object>();
          map.put("msg",MSG);
          map.put("code",CODE);
          return map;
      }
  }
  ```
  
  **总结：**@RequestAttribute("msg") String MSG  把请求中的属性值封装到指定对象中

* @MatrixVariable
  
  * 举例：
    
    ![](C:\Users\DELL\AppData\Roaming\marktext\images\2022-08-19-16-16-59-image.png)
  
  * 应用场景举例：
    
    * 当把 cookie 禁用了，无法得到 sessionid， 从而无法得到 session， 最后无法得到里面的数据（比如：无法得到网页中的数据）

### 2.3 视图接信息与模板引擎

* SpringBoot 不支持 JSP

* src/main/resources/templates 下面放页面
