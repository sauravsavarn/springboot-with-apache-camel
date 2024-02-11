package com.spring.camel.route.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ",", skipFirstLine = true)
@Data
@ToString
public class Item {
    @DataField(pos = 1)
    private String transactionType;

    @DataField(pos = 2)
    private String sku;

    @DataField(pos = 3)
    private String itemDescription;

    @DataField(pos = 4, precision = 2) //precision 2 is needed for 2 place of decimal.
    private BigDecimal price;

    @DataField(pos = 5, columnName = "date-of-purchase")
    private String purchaseDateTime;
}
