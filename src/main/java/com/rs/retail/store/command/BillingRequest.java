package com.rs.retail.store.command;

import com.rs.retail.store.domain.Item;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class BillingRequest {

  private List<Item> items;

  private String customerName;

  private BigDecimal billAmount;


}
