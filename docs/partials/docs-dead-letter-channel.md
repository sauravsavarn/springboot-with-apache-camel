### Dead Letter Channel
* In this part of the docs we will understand how to override the default behaviour of Error Handling and handle it using Dead Letter Channel.
* The concept of Dead Letter Channel is very simple. If there are any exceptions in the route then re-route that message to the <b>Error Node</b>.
  For example, lets consider the below snapshot.

  ![handling-error-exception-through-dead-letter-channel.png](..%2Fassets%2Fimages%2Fhandling-error-exception-through-dead-letter-channel.png)

  > In the above example from snapshot, here the "upstream" sending the data. The Camel Route duty is to validate the data and then sending it to the downstream.
  <br/><br/>If there are any issues then forward that to the <b> Error Node</b>
  
* DeadLetter Channel Example : <br/>
  Here are some of the examples of dead-letter channel :
  ![deadletter-channel-example.png](..%2Fassets%2Fimages%2Fdeadletter-channel-example.png)
  
  > The first thing in above snapshot is :
    >> You will use errorHandler method & then you will provide the method as deadLetterChannel. And inside the deadLetterChannel method, you will provide a URI which is "jms:queue:dead" . The URI from JMS example above, the component we are using here is jms, which is part of Camel JMS library and then you provide the actual queue and the queue name.  
       <br/> So basically if there are any issue happening in the system, then you have the dedicated JMS queue which is going to receive those error messages that are happening in the route using the configuration as show part of JMS section in above snapshot. <BR/>
    
    >> And the next comes the ActiveMQ. As you see in above snapshot, we are using 'activemq' as the component, which is again part of Camel Library. So what you are saying here is that if you have any exception happening in the route, have the configuration as part of the configure method and the camel route. And this deadLetterChannel is going to pickup those exceptions  and followed those exceptions to the 'activemq' component. Basically using active-mq using this configuration. Here it also supports the re-delivery options. Lets say you have an exception and you don't want to react to that exception immediately, you would like to retry and perform some re-tries before you even considering that as a valid exceptions or errors. Even that is also possible with deadLetterChannel. NOTE: these options are not available with default ErrorHandler.
  
    >> Next comes the Kafka Topic. Basically you create a dedicated "errorTopic" and then use the "kafka" component in order to push the messages whatever that are consider as exception to this kafka topic using this deadLetterChannel configuration.   
 
  NOTE: even all those components as discussed above supports the re-delivery options.  


### Enabling the dead letter channel :
  In order to enable the dead-letter channel, we have to put the ErrorHandler AS below :

  >// enable the deadLetterChannel using errorHandler as. NOTe: here we are not using the activemq or kafka topic,etc. 
   <br/>// Just we are logging the message and setting the log-level to 'ERROR'. Also logging all the properties.
  <br/>// Whatever properties that are part of Exchange is to be printed.
  <br/>// THUS, THIS CONFIG BELOW IS GOING TO DISPLAY ALL THE PROPERTIES THAT ARE PART OF THE EXCHANGE.
  <br/><br/> 
  <font color=green>
    errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true"));
  </font>
  <br/><br/>
   REFER : class 'CamelRouteWithProfilesAndDBImplWithDataValidationChecksAndDeadLetterChannelConfig' 
   
  
   <font color="red"><u><b>NOTE:</b></u></font> post configuration of errorHandler as above and in class as mentioned, then in case of exception or error, the Default Error Handler will not be activated to handle exception or error. Always errorHandler configured will take that responsibility.
 
  
   