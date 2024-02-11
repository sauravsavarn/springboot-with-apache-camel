package com.spring.camel.route.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


/**
 * NOTE: Here unlike class CamelRoute where all the from/to/etc are hard-coded. Here in this example we will define
 *       them into profiles and pick it from there.
 */
@Service
@Slf4j // to add logging using lombok
public class CamelRouteWithProfiles extends RouteBuilder {

    @Autowired
    private Environment environment;
    @Override
    public void configure() throws Exception {
//        to add logging into the current code using lombok
        log.info("Starting the Camel Route");
        from("{{startRoute}}")
                .log("Timer Invoked and the body is ${body}" )
//                .log("Timer Invoked and the Processing file ${file:name}" )
                .pollEnrich("{{fromRoute}}")
                .log("the body of read file is - ${body}" )
                .log("the file read is - ${file:name}" )
                .to("{{toRoute1}}")
                .log("from Environment " + environment.getProperty("message"));

        log.info("Ending the Camel Route");

        //NOTE: pollEnrich - //this is basically a fresh poll into the directory 'data/input'. The reason to use the
        //      pollEnrich is that i do not want to use the Exchange which is being send from the timer and want to
        //      construct our own exchange starting this particular node. Meaning it is going to read the data from
        //      the input directory and create the Exchange and use the Exchange created for the route below.

    }
}
