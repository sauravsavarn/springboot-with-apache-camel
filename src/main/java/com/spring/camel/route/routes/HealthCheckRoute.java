package com.spring.camel.route.routes;

import com.spring.camel.route.alert.MailProcessor;
import com.spring.camel.route.processor.HealthCheckProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckRoute extends RouteBuilder {

    @Autowired
    MailProcessor mailProcessor;

    @Autowired
    private HealthCheckProcessor healthCheckProcessor;
    @Override
    public void configure() throws Exception {

        from("{{healthRoute}}").routeId("healthRoute")
                .pollEnrich("http://localhost:8080/actuator/health")
                .process(healthCheckProcessor) //once the endpoint is hit we are calling the HealthCheckProcessor
                .choice()
                    .when(header("error").isEqualTo(true))
                .process(mailProcessor); 
    }
}
