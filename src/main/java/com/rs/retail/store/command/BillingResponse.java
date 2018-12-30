package com.rs.retail.store.command;

import com.rs.retail.store.domain.Item;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingResponse {

  private BigDecimal billAmount;
  private List<Item> items;
  private String customerName;

}
