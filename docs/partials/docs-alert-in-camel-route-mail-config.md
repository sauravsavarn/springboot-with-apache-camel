### Alerting In Camel Route & Mail Config
  ![application-overview-pending-alert-through-mail.png](..%2Fassets%2Fimages%2Fapplication-overview-pending-alert-through-mail.png)
* Here we will discuss about how to send exception as an email event.
* As per the above application overview till now all the nodes have been completed (marked with tick) i.e. node1, node2, node3 and error node completed. The pending item is the email node.
* We will be sending error to mail, when the data validations checks fails (**as shown in diamond box).
* To start with email notification for the error event we need to add the below dependency
  
  ![mail-required-dependency.png](..%2Fassets%2Fimages%2Fmail-required-dependency.png)

* once the email is configured as can be checked in class CamelRoute.java, then in case of any error or exception a mail will be triggered like as below:
  ![email-alerts-on-error-or-exception.png](..%2Fassets%2Fimages%2Femail-alerts-on-error-or-exception.png)

