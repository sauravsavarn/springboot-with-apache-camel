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

*  
* 

