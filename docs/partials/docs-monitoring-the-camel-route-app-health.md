### Health Check of the Camel App
 
* To discuss about how to monitor the health of the app.
  ![application-overview-discussion-health-check.png](..%2Fassets%2Fimages%2Fapplication-overview-discussion-health-check.png)
* As per the application overview, we have different integrations - file , mail & db integrations. In order for the whole application to work, we needs to have all these components up and running.

##### How are we going to make sure all these apps are up and running :
* Spring has a library called Actuators which enables the Health Endpoints for the app, which is what we are going to leverage here to monitor the Health of the App.

### Dependencies required :  
 ![actuators-required-dependency.png](..%2Fassets%2Fimages%2Factuators-required-dependency.png)
 
### Adding health and shutdown properties in the application.yaml
 Below attributes are required to be add into application.yaml
<font color="green"><br/>   
 camel: <br/>
 &emsp;&emsp;&emsp;springboot: <br/>
 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; shutdown-timeout: 2 <br/>
 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; health.indicator.enable: true #with this attribute we are exposing our camel routes 'health' as part of as part of enabling this feature.
</font> <br/><br/>


 The next step is to add the spring actuator properties as below in the application.yaml, in order to access that health endpoints from the browser.
 <font color="green"> <br/>
  management: <br/>
  &nbsp; security:  <br/>
  &emsp;&emsp;&emsp;  enabled: false #making this as false, as if we hit the health endpoint from browser, basically we are going to see what all different components that are running as part of this app, and what are the status of all those components.
 </font> <br/><br/>

> NOTE: after this just start the application and once started post the health endpoints to see the health conditions of different components of our app as below :
      <br/>&nbsp;<font color="yellow"> localhost:<port>/health </font>

> NOTE: In order to check health in the newer spring-boot the following conditions are required:
>    <br/>
     camel: <br/>
&nbsp; &nbsp; springboot: <br/>
&nbsp; &nbsp; &nbsp; &nbsp; shutdown-timeout: 2 <br/>
&nbsp; &nbsp; &nbsp; &nbsp; health.indicator.enable: true  #with this attribute we are exposing our camel routes 'health' as part of as part of enabling this feature. <br/>
management: <br/>
&nbsp; &nbsp; endpoints: <br/>
&nbsp; &nbsp; &nbsp; &nbsp; enabled-by-default: true <br/>
&nbsp; &nbsp; &nbsp; &nbsp; web: <br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; exposure: <br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; include: "*" <br/>
&nbsp; &nbsp; security: <br/>
&nbsp; &nbsp; &nbsp; &nbsp; enabled: false #making this as false, as if we hit the health endpoint from browser, basically we are going to see what all different components that are running as part of this app, and what are the status of all those components. <br/>
<br/>&nbsp;<font color="yellow"> localhost:8080/actuator </font> <br/>

REFER link to understand actuator better : https://docs.spring.io/spring-boot/docs/2.1.11.RELEASE/reference/html/production-ready-endpoints.html

### Automate health checks and Trigger (How to automate this health check functionality) 
* It is required to automate this health check functionality when 1 of the component is down, then send an emil event to the appropriate team who owns the application instead of checking everytime health from the Browser:


