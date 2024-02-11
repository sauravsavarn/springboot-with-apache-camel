### Connecting to postgresDB running in docker container

As per the application overview, [REF: ![application-overview.png](..%2Fassets%2Fimages%2Fapplication-overview.png)]
<br>
till now in branch [*master & * 2.CamelRoutesWithProfiles-And-TestingUsingJUNIT] we have covered both node1 & node2 using apache-camle wiht spring-boot. Also we have setup and checked the app functioning using springboot-test.
Now as per the overview snapshot in this branch we will talk about the node3 which is the database note. Here we are going to use 'POSTGRES' as a database layer and it's connectivity through the apache-camel.

### Spinning postgresDB in docker-container
1. created a docker-compose.yaml file to launch a postgres & pgadmin instance. [PATH OF FILE:: docs/assets/docker-compose-files/spin-postgres-and-pgadmin/docker-compose.yaml]
   Run below command to execute the docker-compose file as: <br/>
   > $> docker-compose --file docker-compose.yaml up -d

### Sets of activity to be part of this branch
In this we will see how to parse the *.csv file & create a java object out of it. Now the question is why we have to parse the *.csv file in the first place. Let's take a look on the application overview again.   
<b>The next activity in the flows is the</b> 
 * Data Validation Check
 * followed by that we have to persists the data into the database

As part of this section we will only be focussing on the persisting the data into the database. 

> In order to persist the data into database we need the individual item from the *.csv file. As per the *.csv file, the individual item corresponds to a single line in the csv file. 
> The first steps is that we are going to convert the *.csv file to a Java Object. We are going to leverage the dependency named 'Camel-Bindy' library which is part of the camel family.
> ![camel-bindy-from-csv-to-javaObject-dependency.png](..%2Fassets%2Fimages%2Fcamel-bindy-from-csv-to-javaObject-dependency.png)
> 

##### camel-bindy library dependency
![camel-bindy-required-dependency.png](..%2Fassets%2Fimages%2Fcamel-bindy-required-dependency.png)

In order to persists the *.csv records from file to the database following steps are taken:
1. first create a domain object named as Item which is having same structure as *.csv which we are going to injest into the application.
2. Then, we are going to map this java object created above into the routes. Let's say for this example we are going to create a separate route named 'CamelRouteWithProfilesAndDBImpl'
   * Here we will first create a reference of DataFormat object from camel-bindy library
   * Now if the Bindy instance is ready the next step is to map it with the Routes.
     > Bindy instance created using line : DataFormat bindy = new BindyCsvDataFormat(Item.class);
     > 
   * Run the test to see the files are created into the output directory 'data/output/'. Also in the logs we can see the list of Items as -<br/> [Item(transactionType=ADD, sku=100, itemDescription=LG TV, price=500.00, purchaseDateTime=2024-02-11T10:08:45.877765), Item(transactionType=ADD, sku=101, itemDescription=AO SMITH, price=100.00, purchaseDateTime=2024-02-11T10:08:45.877765)]
   * Now we will use the camel Splitter EIP to convert the record list which is build by Bindy Library into individual records.
     > for this purpose we are going to use Splitter Enterprise Integration Pattern (in short Splitter EIP). As our job is to split the records list into individual record as stated in below diagram :
       ![camel-split-records-into-individual-record-using-splitter-eip.png](..%2Fassets%2Fimages%2Fcamel-split-records-into-individual-record-using-splitter-eip.png)

##### creation of database & schema in postgresql running inside docker container.
1. first created an application.sql inside the directory 'docs/assets/sql/application.sql', which contains schema creation and schema execute query.
2. we have connected the intellij with the postgres running and execute the script to see results of schema created as below:
   ![application-sql-execute.png](..%2Fassets%2Fimages%2Fapplication-sql-execute.png)

##### Configuring the database in Spring Boot App.
1. to configure teh database in spring boot app following dependencies are required:
   ![configuring-database-from-springboot-required-dependency.png](..%2Fassets%2Fimages%2Fconfiguring-database-from-springboot-required-dependency.png)
2. next data-source is defined as part of config to use to connect to the database.
   check class DBConfig to see the dataSource created inside directory 'src/main/java/com/spring/camel/route/config/DBConfig.java'.
3. then, autowire this data-source from the route class.


### Perform CRUD operation into the database
 > NOTE: to do any database operation following URI to be added to 'to' of the camel as:
    to("jdbc:datasource")

-----------
* Perform INSERT operation ::
        

-----------