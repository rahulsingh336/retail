package com.rs.retail.store.strategy;

import static com.rs.retail.store.common.ApplicationConstants.ONE_HUNDRED;
import static com.rs.retail.store.domain.ItemType.NONGROCERY;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.domain.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PriceCalculationStrategy {

  default BigDecimal calculatePrice(BillingRequest billing){
    Map<Boolean, List<Item>> groups = billing.getItems().stream().collect(
        Collectors.partitioningBy(o -> o.getItemType().equals(NONGROCERY)));
    BigDecimal withOutDiscountSum = calculateTotalForNonGrocery(groups);
    return applyDiscount(withOutDiscountSum).add(calculateTotalForGrocery(groups));
  }

  BigDecimal getDiscount();

  String getDiscountType();

  static BigDecimal percentage(BigDecimal base, BigDecimal pct){
    return base.multiply(pct).divide(ONE_HUNDRED,2, RoundingMode.HALF_UP);
  }

  default BigDecimal applyDiscount(BigDecimal withOutDiscountSum){
    return withOutDiscountSum.subtract(PriceCalculationStrategy.percentage(withOutDiscountSum, getDiscount()));
  }

  default BigDecimal calculateTotalForGrocery(Map<Boolean, List<Item>> groups) {
    return groups.get(false).stream().map(Item::getItemPrice).reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  default BigDecimal calculateTotalForNonGrocery(Map<Boolean, List<Item>> groups) {
    return groups.get(true).stream().map(Item::getItemPrice).reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  default BigDecimal calculateTotalForAllCategories(
      List<Item> items) {
    return items.stream().map(Item::getItemPrice).reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, BigDecimal.ROUND_HALF_UP);
  }

}
