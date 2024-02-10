# Getting Started

### Application Overview
![application-overview.png](assets%2Fapplication-overview.png)

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
![application-overview-with-Junit-Testing.png](assets%2Fapplication-overview-with-Junit-Testing.png)

* Manual checking of whether the files are there in the output directory is NOT IDEAL to test the Camel Routes. 
* Writing test cases with JUNIT is the IDEAL way to check.
*   

# <h4>Lets check the raw out using the Introduction of JUNIT into the Camel Route.
* Until now as per the above condition (as mentioned above without JUNIT), we have been testing our application by manually moving the files into the input directory and our app then move the files from the input-directory to the output-directory. And we checked the files moved or not using manually.
* With the introduction of JUNIT Test-Cases what it is going to do is from the JUNIT we are going to pass the file and place the file into the input-directory and what JUNIT is going to do it is going to start the app which eventually it is going to start the routes. So what all the files are placed into the input-directory by the JUNIT will be moved to the output directory. So once the files are moved into the output directory, the JUNIT is going to check whether the files are successfully moved to the output directory or not by the Camel Route. So JUNIT is going to assert that the files whatever going to sent by JUNIT into the input directories are moved to the output directory. If it is moved to the output directory then we make sure test-case is passed and Camel Route is working as expected. 

________________

To test this using JUNIT follow dependency is required.
![junit-test-required-dependency.png](assets%2Fjunit-test-required-dependency.png)

________________

 