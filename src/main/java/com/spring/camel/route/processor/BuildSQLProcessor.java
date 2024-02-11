package com.spring.camel.route.processor;

import com.spring.camel.route.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

/**
 * Using this class we are going to build the SQL for us.
 */
@Component
@Slf4j
public class BuildSQLProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        //first step is that we are going to get the Item record using the Exchange object.
        Item item = (Item) exchange.getIn().getBody();
        log.info(" Item in Processor is : " + item);

        StringBuilder query = new StringBuilder();
        if(item.getTransactionType().equals("ADD")) {
            query.append("INSERT into items (sku, item_description, price, purchasedatetime) VALUES('");
            query.append(item.getSku()+ "','" + item.getItemDescription() + "'," + item.getPrice() + ",'" + item.getPurchaseDateTime() + "')" );
        } else if(item.getTransactionType().equals("UPDATE")) {
            log.info("<<<<<<<<<<< ITEM-PRICE = " + item.getPrice() + " ----- ITEM-SKUS = " + item.getSku() + " >>>>>>>>> ");
            query.append("UPDATE items set price=" + item.getPrice() + " WHERE sku= '"+item.getSku()+"'");
        } else if(item.getTransactionType().equals("DELETE")) {
            query.append("DELETE from items WHERE sku='" + item.getSku() + "' ");
        }

        //
        log.info("Final Query is : " + query) ;

        //sets the exchange body with the query built.
        exchange.getIn().setBody(query.toString()); //so what we need to do is set the query and pass it to the JDBC object.

    }
}
