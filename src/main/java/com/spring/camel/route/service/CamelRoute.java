package com.spring.camel.route.service;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:hello?period=10s")
                .log("Timer Invoked and the body is ${body}" )
//                .log("Timer Invoked and the Processing file ${file:name}" )
                        .pollEnrich("file:data/input?delete=true&readLock=none")
                            .log("the body of read file is - ${body}" )
                            .log("the file read is - ${file:name}" )
                                .to("file:data/output");
        
        //NOTE: pollEnrich - //this is basically a fresh poll into the directory 'data/input'. The reason to use the 
        //      pollEnrich is that i do not want to use the Exchange which is being send from the timer and want to
        //      construct our own exchange starting this particular node. Meaning it is going to read the data from
        //      the input directory and create the Exchange and use the Exchange created for the route below.

    }
}
