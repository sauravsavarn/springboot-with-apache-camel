package com.spring.camel.route.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  This Processor class is basically going to check the attributes meaning what all the values that are returned from the
 *  response, we are going to access that in the processor and if anyone of that is DOWN then we are going to populate the
 *  Exception property in the Exchange Object.
 *
 */
@Component
@Slf4j
public class HealthCheckProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String healthCheckResult = exchange.getIn().getBody(String.class);

        log.info("Health Check String of the APP is : " + healthCheckResult);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(healthCheckResult, Map.class);

        log.info("map read is " + map);

        StringBuilder builder = new StringBuilder();

        if(map.values().equals("DOWN")) {
            builder.append("Exception : application health is down");

            log.info("Exception : application health is down  ");

            //setting exchange header
            exchange.getIn().setHeader("error ", true );
            exchange.getIn().setBody(builder.toString());
            exchange.setProperty(Exchange.EXCEPTION_CAUGHT, builder.toString() );
        }



    }
}
