package com.rs.retail.store.strategy;

import static com.rs.retail.store.common.ApplicationConstants.AFFILIATE;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class AffiliatePriceCalculationStrategy implements PriceCalculationStrategy {

  @Override
  public BigDecimal getDiscount() {
    return new BigDecimal(10);
  }

  @Override
  public String getDiscountType() {
    return AFFILIATE;
  }
}
