# Spring Cloud Config

## What is Spring Cloud Config

Spring Cloud Config provides server-side and client-side support for externalized configuration in a distributed system. With the Config Server, you have a central place to manage external properties for applications across all environments. This configuration store is ideally versioned under Git version control and can be modified at application runtime. While it fits very well in Spring applications using all the supported configuration file formats together with constructs like <b><i>Environment, PropertySource or @Value</i></b>, it can be used in any environment running any programming language.

## Spring Cloud Config using Git

### Config server implementation

For the service application, we use Maven to generate a new project with the required dependency (Config Server).  

Dependencies and dependency managment in pom.xml:

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>

<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>${spring-cloud.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

The main part of the application is a config class – more specifically a <b><i>@SpringBootAplication</i></b> which pulls in all the required setup through the auto-configure annotation <b><i>@EnableConfigServer</b></i>.

Main class for server application:

```java
@SpringBootApplication
@EnableConfigServer
public class CentralizedConfigurationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralizedConfigurationServiceApplication.class, args);
	}

}
```

After adding these two annotations, it is necessary to define ``application.properties`` file. This file is located in <i>src/main/resources</i>. Like all Spring Boot applications, it runs on port 8080 by default, but you can switch it to the more conventional port 8888 in various ways.

``application.properties`` file:

```
server.port=8888
spring.cloud.config.server.git.uri= C:/Users/Sofi/Desktop/configrepo
spring.application.name=configFile
```

In this example, the port has been changed to 8888. Git-url provides our version-controlled configuration content. 

<i>${user.home}/configrepo</i> is a git repository containing yaml or properties files.

Before starting the application, it is necessary to create a new git repository for storing yaml/properties file. First, create new folder and new file (``configFile.properties``) and then add that file to new repository and commit it. 

Creating new repository with ``git bash``:

```bash
git init 
git add .
git commit -m "Added configFile"
```

The name of the file is ``configFile``. That is our configuration for the application and we can define whatever we want for our application. In this example, page size has been defined. 

```
pageSize=2
```

Now we're able to start our server. The Git-backed configuration API provided by our server can be queried using the following paths:

```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

In which the <i>{label}</i> placeholder refers to a Git branch, <i>{application}</i> to the client's application name and the <i>{profile}</i> to the client's current active application profile.

```bash
curl localhost:8888/configFile/default
```

### The Client Implementation

Now when a Config Server has been done, you need to stand up a new Spring Boot application that uses the Config Server to load its own configuration and that refreshes its configuration to reflect changes to the Config Server on-demand, without restarting the JVM. To do so, add the ``org.springframework.cloud:spring-cloud-starter-config`` dependency, to connect to the Config Server. Spring sees the configuration property files, as it would any property file loaded from application.properties or application.yml or any other PropertySource.

The configuration, to fetch our server, must be placed in a resource file named ``bootstrap.properties``, because this file (like the name implies) will be loaded very early while the application starts. In addition to the application name, we also put the connection-details in our bootstrap.properties:

``` 
spring.application.name=configFile
spring.cloud.config.uri=http://localhost:8888
management.endpoints.web.exposure.include= *
spring.cloud.config.allow-override=true
endpoints.actuator.enabled=true
```
You also want to enable the ``/refresh`` endpoint, to demonstrate dynamic configuration changes. The listing above shows how to do so via the ``management.endpoints.web.exposure.include`` property.

The client can access any value in the Config Server by using  <b><i>@Value("${…​}")</b></i>

``` java
@Value("${pageSize}")
	private Integer  pageSize;
```

By default, the configuration values are read on the client’s startup and not again. You can force a bean to refresh its configuration (that is, to pull updated values from the Config Server) by annotating with the Spring Cloud Config ``@RefreshScope`` and then triggering a refresh event. The following listing shows how to do so:

``` java
@Controller
@RefreshScope

public class BookC implements BooksApi {

	@Autowired
	BookService bookService;

	@Autowired
	ModelMapper modelMapper;
```
### Test the application

You can test the end-to-end result by starting the Config Server first and then, once it is running, starting the client. Visit the client app in the browser at ``http://localhost:8080/books``. There, you should see 2 books in the response.

Change the pageSize key in the ``configFile.properties`` file in the Git repository to something different (5 for example). You can confirm that the Config Server sees the change by visiting http://localhost:8888/configFile/default. You need to invoke the refresh Spring Boot Actuator endpoint in order to force the client to refresh itself and draw in the new value. Spring Boot’s Actuator exposes operational endpoints (such as health checks and environment information) about an application. To use it, you must add ``org.springframework.boot:spring-boot-starter-actuator`` to the client application’s classpath. You can invoke the refresh Actuator endpoint by sending an empty HTTP POST (in some cases, you have to use an empty HTTP OPTIONS) to the client’s refresh endpoint: ``http://localhost:8080/actuator/refresh``. Then you can confirm it worked by visting the ``http://localhost:8080/books`` endpoint.

The following command invokes the Actuator’s refresh command:

```
$ curl POST localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"
```

## Spring Cloud Config using JDBC

### Config Server Implementation

This example focuses on composite configuration in spring cloud config with backend as Jdbc. Spring Cloud Config Server supports JDBC (relational database) as a backend for configuration properties. You can enable this feature by adding spring-jdbc to the classpath and using the jdbc profile or by adding a bean of type JdbcEnvironmentRepository. If you include the right dependencies on the classpath, Spring Boot configures a data source. It supports Postgres, Mysql and other RDBMS and NoSql.

Dependencies in pom.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.4</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>centralized-configuration-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>centralized-configuration-service</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
		<spring-cloud.version>2020.0.2</spring-cloud.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
			<version>2.4.4</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

Before definig application.properties file we need to create a new table in database (our database is configFile).

``` sql
create table PROPERTIES (
  id serial primary key, 
  CREATED_ON timestamp ,
  APPLICATION text, 
  PROFILE text, 
  LABEL text, 
  PROP_KEY text, 
  VALUE text
 );
```

And insert two records:

```
INSERT INTO properties (CREATED_ON, APPLICATION, PROFILE, LABEL, PROP_KEY, VALUE) VALUES (NULL,'configFile','dev','latest','pageSize','5');
INSERT INTO properties (CREATED_ON, APPLICATION, PROFILE, LABEL, PROP_KEY, VALUE) VALUES (NULL,'configFile','prod','latest','pageSize','2');
```

JDBC works with key pattern ``<application-name>/<profile>/<label>``.
In this case <b><i>configFile/dev/latest</b></i>. To see the values in config server we have to invoke api in server with <b><i><base-uri>/configFile/dev/latest</b></i>.

Now, our application.properties file looks:

```
server.port=8888
spring.profiles.active=jdbc
spring.datasource.url=jdbc:postgresql://localhost:5432/configFile
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
spring.jpa.hibernate.show-sql=true
spring.datasource.username=postgres
spring.datasource.password=socaacos
spring.cloud.config.server.jdbc.sql=SELECT PROPERTIES.PROP_KEY, PROPERTIES.VALUE from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?
```

Where <i>Application, Profile</i> and <i>Label</i> we have defined at Client Side.
We define port where is server application located, url for our database and username / password for connecting to the database. In this case, we use postgres Database.

Of course, we have to add two annotations @SpringBootApplication and @EnableConfigServer in main class (same as in the first example). 

Now, we can start our server by calling :

```
curl http://localhost:8888/configFile/dev/latest
or
curl http://localhost:8888/configFile/prod/latest
```
### Client Implementation

For client application, it is necessary to create new Maven project and add these dependencies:

```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

After that, we can define our bootstrap.properties file. The file looks:

```
management.endpoints.web.exposure.include= *
spring.cloud.config.allow-override=true
endpoints.actuator.enabled=true
spring.application.name=configFile
spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.profile=dev
spring.cloud.config.label=latest
```

In this file, we define which profile is active (in our case, we have two profiles -prod and dev), label and application.name.

Other steps are same as for using Git.


