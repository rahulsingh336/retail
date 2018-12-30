package com.rs.retail.store.strategy;

import static com.rs.retail.store.common.ApplicationConstants.EMPLOYEE;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class EmployeePriceCalculationStrategy implements PriceCalculationStrategy {


  @Override
  public BigDecimal getDiscount() {
    return new BigDecimal(30);
  }

  @Override
  public String getDiscountType() {
    return EMPLOYEE;
  }
}
