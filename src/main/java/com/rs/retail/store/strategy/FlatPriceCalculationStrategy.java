package com.rs.retail.store.strategy;

import static com.rs.retail.store.common.ApplicationConstants.FLAT;
import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.common.ApplicationConstants;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FlatPriceCalculationStrategy implements PriceCalculationStrategy {

  @Override
  public BigDecimal calculatePrice(BillingRequest billing) {
    BigDecimal withOutDiscountSum = calculateTotalForAllCategories(billing.getItems());
    return applyFlatRate(withOutDiscountSum);
  }

  private BigDecimal applyFlatRate(BigDecimal withOutDiscountSum) {
    BigDecimal hundredMultiplier = withOutDiscountSum.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
    return BigDecimal.ZERO == hundredMultiplier ?  withOutDiscountSum.setScale(2, BigDecimal.ROUND_HALF_UP)
        : withOutDiscountSum.subtract(new BigDecimal((int)hundredMultiplier.doubleValue() * ApplicationConstants.INT_FIVE)).setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  @Override
  public BigDecimal getDiscount() {
    return new BigDecimal(5);
  }

  @Override
  public String getDiscountType() {
    return FLAT;
  }

}
