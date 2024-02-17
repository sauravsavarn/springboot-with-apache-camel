# Getting Started

### Application Overview
![application-overview.png](assets%2Fimages%2Fapplication-overview.png)

Above application going to build with spring-boot.

* Here the flow starts with upstream.
* The upstream is going to send the bunch of files into the directory data/input
* And we have the camel route which is going to start with the Timer which is going to run every 10 seconds. This is going to poll the input directory every 10 seconds. And if there are any new files that file is going to move to node2.
* And then the data validation is performed in the file content and if the validation is successful then the content is moved to the DB. Otherwise if the validation is having some issues then email is send to the configured email address and also the files will be moved to a directory called input/error.
________________
As you can see there are 3 different types of nodes here <br/>  
    1. the file node whcih are nodes as - data/input, data/output & input/error. <br/>
    2. another one is the DB node. <br/>
    3. last one is the mail node. <br/>
________________


### To testing the Application using JUNIT, now the application overview is changed and as below:
![application-overview-with-Junit-Testing.png](assets%2Fimages%2Fapplication-overview-with-Junit-Testing.png)

* Manual checking of whether the files are there in the output directory is NOT IDEAL to test the Camel Routes. 
* Writing test cases with JUNIT is the IDEAL way to check.
*   

# <h4>Lets check the raw out using the Introduction of JUNIT into the Camel Route.
* Until now as per the above condition (as mentioned above without JUNIT), we have been testing our application by manually moving the files into the input directory and our app then move the files from the input-directory to the output-directory. And we checked the files moved or not using manually.
* With the introduction of JUNIT Test-Cases what it is going to do is from the JUNIT we are going to pass the file and place the file into the input-directory and what JUNIT is going to do it is going to start the app which eventually it is going to start the routes. So what all the files are placed into the input-directory by the JUNIT will be moved to the output directory. So once the files are moved into the output directory, the JUNIT is going to check whether the files are successfully moved to the output directory or not by the Camel Route. So JUNIT is going to assert that the files whatever going to sent by JUNIT into the input directories are moved to the output directory. If it is moved to the output directory then we make sure test-case is passed and Camel Route is working as expected. 

________________

To test this using JUNIT follow dependency is required.
![junit-test-required-dependency.png](assets%2Fimages%2Fjunit-test-required-dependency.png)

________________

### Running Test class

 ![test-class-written-to-UNIT-Test-app.png](assets%2Fimages%2Ftest-class-written-to-UNIT-Test-app.png)
 
* above snapshot shows the test class written to UNIT Test the application.
* To build and run the test all in go using Gradle, click on the build as show. This will first start the applcation and then execute Tests on it and yields the final *.jar artifacts. <br/>
![click-to-build-from-Gradle-menu.png](assets%2Fimages%2Fclick-to-build-from-Gradle-menu.png)

### Enable logging in SpringBoot using LogBack & Lombock
* By default spring boot provides libraries for logging which is:
  * logback-classic
  * logback-core

* to enable file based logging, create a file called logback.xml in resources directory
* no-sooner logback.xml is put into resources, when springboot starts it is automatically going to load all the configuration whatever defined into this file.  
  * When using starters, Logback is used for logging by default. <br/>
    >   NOTE: Spring Boot preconfigures it with patterns and ANSI colors to make the standard output more readable.

      ### Logback Configuration Logging
    Even though the default configuration is useful (for example, to get started in zero time during POCs or quick experiments), it’s most likely not enough for our daily needs.

    Let’s see <b>how to include a Logback configuration</b> with a different color and logging pattern, with separate specifications for console and file output, and with a decent rolling policy to avoid generating huge log files.

    First, we should find a solution that allows for handling our logging settings alone instead of polluting application.properties, which is commonly used for many other application settings.

    <b>When a file in the classpath has one of the following names, Spring Boot will automatically load it</b> over the default configuration:
    *  logback-spring.xml
    *  logback.xml
    *  logback-spring.groovy
    *  logback.groovy
    
    >Spring recommends using the -spring variant over the plain ones whenever possible, as described here.

    Let’s write a simple logback-spring.xml:
  ![logback-xml-configuration.png](assets%2Fimages%2Flogback-xml-configuration.png)
    > Output:
        > ![logback-run-output.png](assets%2Fimages%2Flogback-run-output.png)
    >

* Enable lombok and use it for logging application related info.
  * For this we have to use @Slf4JLombok, which helps to generate logger for a class.
    * For clarity see below picture where it is clearly shown the logging for a class with and without lombok.
    > class with lombok will have @Slf4j annotation on top of class.
      <br/>class without lombok will have a logger instance created in the beginning of the class.
      ![class-with-and-without-lombok-for-logging.png](assets%2Fimages%2Fclass-with-and-without-lombok-for-logging.png)
  * In order to use lombok following dependency to be added into the project.
    ![lombok-required-dependency.png](assets%2Fimages%2Flombok-required-dependency.png)

> To use lombok in intellij we have to enable the annotation-processing. To do this go to the intellij Settings or Preferences and enable the annotation-processing as per the below snapshot.
    ![intellij-enable-annotation-processing.png](assets%2Fimages%2Fintellij-enable-annotation-processing.png)


### Connecting to postgresDB running in docker container
REFER the attached *.md link to see detail explanation for connecting to the postgresSQL database from camel in spring-boot <br/>
[docs-connecting-to-postgres.md](partials%2Fdocs-connecting-to-postgres.md)

### Error/Exception Handling In Camel Routes
REFER to know all about error & exception handling through Camel Routes.
[docs-errorORexception-handling-in-camel-routes.md](partials%2Fdocs-errorORexception-handling-in-camel-routes.md)

### Alerting In Camel Route & Email Config
[docs-alert-in-camel-route-mail-config.md](partials%2Fdocs-alert-in-camel-route-mail-config.md)

### Monitoring The Camel Route App Health
[docs-monitoring-the-camel-route-app-health.md](partials%2Fdocs-monitoring-the-camel-route-app-health.md)

### Building Kafka Camel Route as Kafka -> DB -> mail
[docs-build-kafka-camel-route-for-kafka-db-mail.md](partials%2Fdocs-build-kafka-camel-route-for-kafka-db-mail.md)
