### Building Kafka Camel Route as Kafka -> DB -> mail
![application-overview-building-kafka-camel-route as kafka-db-mail.png](..%2Fassets%2Fimages%2Fapplication-overview-building-kafka-camel-route%20as%20kafka-db-mail.png)
 * As per the above architecture where we are going to build a new application called 'retail inventory system' using apache camel, kafka, db & mail.
 * In this retail inventory system, the items will flow from the upstream. Here, items can be brand new items or update to the existing item or removing an item from the Inventory.
 * The source of the Upstream, could be from a bunch of users pushing the message to the Kafka Topic or some other system pushing the message to the Kafka Topic.   
 * The kafka topic name is 'input_item_topic'.
 * The next thing as per the application architecture is that camel route is going to pick messages from the 'input_item_topic' and then perform the data validation. If the data validation is successful then we are going to persists the data into the database. Otherwise if the data validation fails, then push the message to the Kafka Topic 'error_topic' which is designated for receiving the error message and then send an email event to the configured email address.    

### Dependencies
    <!-- https://mvnrepository.com/artifact/org.apache.camel.springboot/camel-kafka-starter -->
    <dependency>
        <groupId>org.apache.camel.springboot</groupId>
        <artifactId>camel-kafka-starter</artifactId>
        <version>4.3.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.apache.camel/camel-kafka -->
    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-kafka</artifactId>
        <version>4.3.0</version>
    </dependency>

* above two dependencies are necessary in order to talk to Kafka from the Camel Routes.

### startRoute configuration for Kafka

kafka:input_item_topic?brokers=localhost:9092&groupId=1&autoOffsetReset=earliest&consumersCount=1  

* Here autoOffsetReset=earliest meaning this kafka topic comes up, have that offset reset to the earlier value in that particular topic. 
* Here consumersCount=1 

### Data Contracts that we are going to receive from the UpStream system
![app2-data-contracts-from-upstream-system.png](..%2Fassets%2Fimages%2Fapp2-data-contracts-from-upstream-system.png)

* As it is displayed in the diagram, we are going to receive the items as the JSON string from the KafkaTopic.  
* Let's take a look on the different types of inputs (i.e. data contract) that we are going to receive from the input system :
    [data-contract.data](..%2F..%2Fdata%2Finput%2Fdata-contract.data)

      {"transactionType":"ADD", "sku":"100", "itemDescription": "Samsung TV", "price": "500.00"}
      {"transactionType":"ADD", "sku":"101", "itemDescription": "LG TV", "price": "500.00"}
      {"transactionType":"UPDATE", "sku":"101", "itemDescription": "LG TV", "price": "700.00"}
      {"transactionType":"DELETE", "sku":"101", "itemDescription": "LG TV", "price": "500.00"}

      --Error
      {"transactionType":"ADD", "sku":"", "itemDescription": "ABC TV", "price": "500.00"}

* In above Error scenario, we consider that one as a Failure and then we send the data into ErrorTopic 'error_topic' & also send that event to the configured email address as per application overview attached above in the beginning.


### Parse JSON to JAVA Object using Gson lib
![parse-gson-to-java-object.png](..%2Fassets%2Fimages%2Fparse-gson-to-java-object.png)

* for this we also required to add dependency camel-gson lib as below

      <!-- https://mvnrepository.com/artifact/org.apache.camel/camel-gson -->
      <dependency>
          <groupId>org.apache.camel</groupId>
          <artifactId>camel-gson</artifactId>
          <version>4.3.0</version>
      </dependency>

### Input message Data Validation
* if the sku number is empty then this is an error record.

      {"transactionType":"ADD", "sku":"", "itemDescription": "ABC TV", "price": "500.00"}
  In the above such scenarios we have to perform the data validation.

### Post implementation Steps :
* Post implementation of below architecture 
  ![application-overview-building-kafka-camel-route as kafka-db-mail.png](..%2Fassets%2Fimages%2Fapplication-overview-building-kafka-camel-route%20as%20kafka-db-mail.png)
* Our requirement has been updated with the new requirements entails where in addition to just persisting the data into the database we need to print the data that just got persisted.  
  ![app2-updated-application-overview-building-kafka-camel-route as kafka-db-mail.png](..%2Fassets%2Fimages%2Fapp2-updated-application-overview-building-kafka-camel-route%20as%20kafka-db-mail.png)  

  For that purpose we are adding a new dependency :
  ![printing-data-post-db-persistence-dependency.png](..%2Fassets%2Fimages%2Fprinting-data-post-db-persistence-dependency.png)
* 