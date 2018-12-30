package com.rs.retail.store.strategy;

import static com.rs.retail.store.common.ApplicationConstants.LONG;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class LongServicePriceCalculationStrategy implements PriceCalculationStrategy {

  @Override
  public BigDecimal getDiscount() {
    return new BigDecimal(5);
  }

  @Override
  public String getDiscountType() {
    return LONG;
  }
}
