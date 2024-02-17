package com.spring.camel.route.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Note here all the Item properties needs to be matched from what we have received from the Kafka Topic.
 */
@Data
@ToString
public class Item {
    private String transactionType;
    private String sku;
    private String itemDescription;
    private BigDecimal price;
    private String purchaseDateTime;
}
