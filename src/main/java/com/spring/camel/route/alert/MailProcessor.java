package com.spring.camel.route.alert;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * This class implements a Processor because  when the route fails we are calling this processor.
 * This Processor is going to take care of sending exception context to the mail account.
 */
@Component
@Slf4j
public class MailProcessor implements Processor {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Environment environment;

    /**
     *
     * @param exchange - The Exchange argument will have the actual exception that is being thrown from our application.
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        //to get the actual exception
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        log.info("Exception caught in mail processor : " + e.getMessage());
        
        //message body for email body that we are going to send.
        String messageBody = "Exception happened in the Camel Route : " + e.getMessage();

        //
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("mailFrom"));
        message.setTo(environment.getProperty("mailTo"));
        message.setSubject("Exception in Camel Route");
        message.setText(messageBody);

        //finally send the message using Java Mail Sender API.
        javaMailSender.send(message);

    }
}
