package com.spring.camel.route.service;

import com.spring.camel.route.domain.Item;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "dev")
@RunWith(CamelSpringBootRunner.class) //that is which class this Test to run with.
@SpringBootTest //since this is a SpringBoot application, hence added the annotation.
public class KafkaCamelRouteTest {

    @Autowired
    ProducerTemplate producerTemplate;
    //is used to produce some files and we are using this to the file into the input directory.

    @Autowired
    ConsumerTemplate consumerTemplate;

    @Autowired
    Environment environment;

    @Test
    public void kafkaRoute_PositiveTest() {
        String input = "{\"transactionType\":\"ADD\", \"sku\":\"700\", \"itemDescription\": \"Samsung TV\", \"price\": \"500.00\", \"purchaseDateTime\": \"2024-02-11T18:46:03.195151\"}";

        //
        String response = (String) producerTemplate.requestBody("kafka:input_item_topic?brokers=localhost:9092&clientId=2", input);

        assertNotNull(response); //if this is notnull this means that the record is successfully persisted into the topic & db.
    }

    /**
     * Here we are passing the item where sku is null. In such scenario we are doing 2 things. First, we are going to send the email and then Secondly, sending it to the topic 'error_topic'.
      */
    @Test
    public void kafkaRoute_NegativeTest() {

    String input =
        "{\"transactionType\":\"ADD\", \"sku\":\"\", \"itemDescription\": \"Samsung TV\", \"price\": \"500.00\", \"purchaseDateTime\": \"2024-02-11T18:46:03.195151\"}";

        //
        producerTemplate.requestBody("kafka:input_item_topic?brokers=localhost:9092", input);

        //
        String response = (String) consumerTemplate.receiveBody("kafka:error_topic?brokers=localhost:9092");

        System.out.println("Response is : " + response); //printing response received into the error_topic.

        assertNotNull(response); //this means if the error_topic is able to receive the data then some content being pushed into the error_topic because the data is invalid.
    }

}
