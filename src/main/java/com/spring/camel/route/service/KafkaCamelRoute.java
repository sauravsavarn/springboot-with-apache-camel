package com.spring.camel.route.service;

import com.spring.camel.route.alert.MailProcessor;
import com.spring.camel.route.domain.Item;
import com.spring.camel.route.exception.DataException;
import com.spring.camel.route.processor.BuildSQLProcessor;
import com.spring.camel.route.processor.SuccessProcessor;
import javax.sql.DataSource;

import com.spring.camel.route.processor.ValidateDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaCamelRoute extends RouteBuilder {

    @Autowired
    private Environment environment;

    //wiring this dataSource bean in order to connect to the database, for which all config parameters defined in application.yaml file.
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    private SuccessProcessor successProcessor;

    @Autowired
    private ValidateDataProcessor validateDataProcessor;

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Autowired
    private MailProcessor mailProcessor; //Bean auto-wired for sending exception events to the mail as per application overview architecture


    @Override
    public void configure() throws Exception {
//        to add logging into the current code using lombok
        log.info("Starting the Camel Route");

        //this is required to convert JSON to JAVA Object.
        GsonDataFormat itemFormat = new GsonDataFormat(Item.class);

        //
        onException(PSQLException.class).log(LoggingLevel.ERROR, "PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        //What happens below is that if there is a DataException or RuntimeException or both, then log that error. In addition to that send that event as
        //an email using the 'mailProcessor'.
        //Thus under the scenario during the data-exception what it is going to do. It is going to the mailProcessor
        //and in the mailProcessor the process method is going to get invoked, where we have mailSender completely loaded 
        //with the mail configuration using which to trigger mail to the sender with all exception/error events info.
        //In addition to this we are also sending that error to the topic 'error-topic'.
        onException(DataException.class, RuntimeException.class).log(LoggingLevel.ERROR, "Data Exception in the route ${body}").process(mailProcessor)
                        .to("{{errorRoute}}");

        from("{{fromRoute}}").routeId("mainRoute") ///adding routeId for the APP.
                .log("Current environment is - " + environment.getProperty("message") )
                .log("Read Message from kafka - ${body}" )
                .unmarshal(itemFormat)
                .log("Unmarshalled message is ${body}")
                    .process(validateDataProcessor)
                    .process(buildSQLProcessor)
                    .to("{{toRoute2}}") //this is the route where we are persisting the data.
                    .to("sql:select * from items where sku= :#skuID?dataSource=#dataSource") //here it is to print the data post data saved into DB. with :#skuID means to take the skuID from the Exchange Header which is set into class BuildSQLProcessor. Post skuID we required to pass the dataSource.
                    .log("Read item from db is ${body}"); //finally logging/printing the select query

        log.info("Ending the Camel Route");

    }
}
