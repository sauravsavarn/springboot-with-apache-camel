### How Camel handles error or any exception in the route(s)

* Camel handles any error or exception in the route by default. Thus not required to add any other libraries or logic to the route(s).
  > e.g. snapshot below shows the error/exception handled by the camel within it's routes as:
     ![handling-error-exception-camel.png](..%2Fassets%2Fimages%2Fhandling-error-exception-camel.png)
  NOTE: Here as per the snapshot the exception is handled automatically by Camel class 'CamelLogger'.

### Discuss about different ways of handling the exception(s) which are more rich ways of exception handling in the route(s) :


### Error Handling & it's LifeCycle in Camel :
* this helps us to understand how error handling module in Camel works.  
* As we know that the most generic use-case of Camel is to <b><u>integrate with different systems</u></b>
* Integrating systems over the network has it's own challenges, where following things can happen like :
  * Network could be down.
  * Network could be very slow.
  * Requests may fail for no reason.
* Thus different ways of handling exceptions/errors in Camel in order to handle the adverse scenarios, like for example discussed above, in a camel route.
  * The generic errors are categorised into two categories :
    1. Recoverable Errors
       >   Recoverable errors are something which can happen like - <br/>
           1. Schedule maintenance activity on a system which makes the system not reachable for sometime. <br/>
           2. Some Temporary network issues which may happen in your route.
    2. Irrecoverable Errors
       >   These are the errors which occurs : <br/>
           1. When you are trying to connect to a database which does not even exists. <br/>
           Example: lets say if you have provided a server details in your application which is not valid at all. 
              Valid Server is (for say) : xyz.db.com
              But in your application you have given DB Server Address as:
              Invalid Server address (for say) : xyz1.db.com
              <br/><font color="red"><u>thus in this way there is no way to recover from such error.</u></font>
           <br/><br/>2. Trying to make an api call to a REST Endpoint which does not even exists.   
             <br/>Example: <br/>
             Valid Server - http://xyz.com:8080/xyz
             Invalid Server (for example tried is) - http://xyz.com:8081/xyz  

* Camel error handling is part of the Camel Core itself.
  ![handling-error-handling-camel-route.png](..%2Fassets%2Fimages%2Fhandling-error-handling-camel-route.png)
   
   In the above snapshot example we have 3 nodes. 
   1. node1
   2. node2
   3. node3 
   where route starts from node1 and then it moves to node2 & node3. <br/>
<font color="green"><b><u>Let's say you have some exception happen at node2. In that case what happens is the Camel populate the actual Exception in the Exchange Object and then it calls the DefaultErrorHandler.</u></b></font> As we know that Camel builds an Exchange Object and send to the node(s). The birth of the Exchange Object actually happens in the starting node where as per the above snapshot the starting node is 'node n1'.  

* Quick Look about the Exchange Object:
  ![camel-exchange-object-structure.png](..%2Fassets%2Fimages%2Fcamel-exchange-object-structure.png)
  
  As per the above snapshot the Exchange Object has the following entity where we are trying to move the files from the Input Directory to the Output Directory as per the snapshot.
  1. Exchange ID
  2. Message Exchange Pattern
  3. Exception
  4. Properties
  5. Message 
  
  Thus, from the above snapshot it is evident that the Exchange Object is constructed after the Camel starts the route which in turn reads the file from the Input Directory. 
  
  > Let's say in the above example if something happens to the output directory which is 'to' Node, in that case the actual exception will be populated in the Exception property and then send it to the Error Handler. If you want to create an Alert or an Event based on the exception, then it is possible by using the exception property in the Exchange Object. 

  <font color="green"><b>Both Recoverable & Irrecoverable errors/exceptions can be accessed from the Exchange Object.</b></font>
  
### Lifecycle of Error Handling module in Camel :
 
###### Camel's Scope Of Error Handling -
 
 * Camel Scope of Error Handling is only between the route.
 * If you have any Exception outside the camel route then it will not be handled by Camel.
 
  e.g. Lets take a look at the example :
   ![camel-scope-of-error-handling.png](..%2Fassets%2Fimages%2Fcamel-scope-of-error-handling.png)
   
   where you have Camel route of 3 Nodes and the route starts from node1 which is the starting node for the Camel Route. If you have any exception happen at  node1, say you are going to copy file from the input directory and then move to the other nodes. In that case if you have any issues reading the file from the Input Directory itself then in that case Camel Default Error Handling module wont be invoked and Camel will not handle that Exception.   
    


### TASK -
   Here as part of the coding exercise we will handle any data related validations for the CSV file that is going to be received from the upstream systems we have build as per the application architecture.
   ![application-overview.png](..%2Fassets%2Fimages%2Fapplication-overview.png)
   
   So the use-case here is that if the SKU number is null, then the data exception will be thrown from the Camel Route.  
