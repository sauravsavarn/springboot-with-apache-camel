package com.spring.camel.route.service;

import com.spring.camel.route.domain.Item;
import com.spring.camel.route.processor.BuildSQLProcessor;
import com.spring.camel.route.processor.SuccessProcessor;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CamelRouteWithProfilesAndDBImplWithDataValidationChecksAndDeadLetterChannelConfig extends RouteBuilder {

    @Autowired
    private Environment environment;

    //wiring this dataSource bean in order to connect to the database, for which all config parameters defined in application.yaml file.
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Autowired
    private SuccessProcessor successProcessor;

    @Override
    public void configure() throws Exception {
//        to add logging into the current code using lombok
        log.info("Starting the Camel Route");

//        Data Format
        DataFormat bindy = new BindyCsvDataFormat(Item.class);

        // enable the deadLetterChannel using errorHandler as. NOTe: here we are not using the activemq or kafka topic,etc. 
        // Just we are logging the message and setting the log-level to 'ERROR'. Also logging all the properties. 
        // Whatever properties that are part of Exchange is to be printed.
        // THUS, THIS CONFIG BELOW IS GOING TO DISPLAY ALL THE PROPERTIES THAT ARE PART OF THE EXCHANGE.
//        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true&showAllProperties=true"));
        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showAllProperties=true"));
        //another version we can have is re-delivery and re-try
//        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showAllProperties=true").maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR));
         
        from("{{startRouteForDBImplWithDataValChecksAndDLChannelConfig}}")
                .log("Timer Invoked and the body is ${body}" )
//                .log("Timer Invoked and the Processing file ${file:name}" )
                .pollEnrich("{{fromRouteForDBImplWithDataValChecksAndDLChannelConfig}}")
                .log("the body of read file is - ${body}" )
                .log("the file read is - ${file:name}" )
                .to("{{toRoute1}}")
                .log("from Environment " + environment.getProperty("message"))
                .unmarshal(bindy) //unmarshal is used to convert the csv file to list of Java Object
                .log("The unmarshalled object is : ${body}") //to verify the unmarshalled is Java Object we have used this log.
                .split(body()) //here body() inside split method gives access to the body from the Exchange Object and then split the records list into individual record.

                    /////////AT THIS POINT POST SPLIT WE HAVE THE INDIVIDUAL RECORDS, DATA VALIDATION CAN BE ONLY DONE HERE WHEN WE HAVE THE INDIVIDUAL RECORDS #####
                    /////////THUS WE WILL BUILD THIS INTO CLASS BuildSQLProcessor. #########

                    .log("individual Record is : ${body}")
                    //.process(new BuildSQLProcessor()) //giving the sql query through the processor.
                    .process(buildSQLProcessor) //giving the sql query through the processor.
    //                    .to("jdbc:dataSource") //NOTE : moved this to the application.yaml as key-value pair. thus referring only in next line.
                    .to("{{toRoute2}}")
                .end()
                .process(successProcessor)
                //.process(new SuccessProcessor()); //just to inform the route & unit-test case as well about SUCCESS. What is happening is
                //actually when the Insertion is successful and if there are no-issues then the call is
                //going to go to the SuccessProcessor. If there are any issues then the call will
                //not reach this particular node and the route.
                .to("{{toRoute3}}"); //then the next step is to write the result into the folder.
        log.info("Ending the Camel Route");

        //NOTE: pollEnrich - //this is basically a fresh poll into the directory 'data/input'. The reason to use the
        //      pollEnrich is that i do not want to use the Exchange which is being send from the timer and want to
        //      construct our own exchange starting this particular node. Meaning it is going to read the data from
        //      the input directory and create the Exchange and use the Exchange created for the route below.

    }
}
