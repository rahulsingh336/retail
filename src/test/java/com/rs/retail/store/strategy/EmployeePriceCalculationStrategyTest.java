package com.rs.retail.store.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.domain.Customer;
import com.rs.retail.store.domain.Item;
import com.rs.retail.store.domain.ItemType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by e076103 on 20-12-2018.
 */
public class EmployeePriceCalculationStrategyTest {

  private static final String MOCK = "Mock";
  private static final String MOCK_ITEM = "MOCK_ITEM";
  private static final BigDecimal ITEM_PRICE = new BigDecimal(100.00);

  private EmployeePriceCalculationStrategy employeePriceCalculationStrategy;


  @Before
  public void setUp() throws Exception {
    employeePriceCalculationStrategy = new EmployeePriceCalculationStrategy();
  }

  @Test
  public void calculatePriceTest() throws Exception {
    BillingRequest billingDto = new BillingRequest();
    billingDto.setCustomerName(MOCK);
    Item item = new Item();
    item.setItemPrice(ITEM_PRICE);
    item.setItemType(ItemType.NONGROCERY);
    item.setItemName(MOCK_ITEM);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingDto.setItems(itemList);

    BigDecimal return_value =  employeePriceCalculationStrategy.calculatePrice(billingDto);

    BigDecimal bill_amt = new BigDecimal(70.00).setScale(2, BigDecimal.ROUND_HALF_UP);
    assertThat(return_value).isEqualTo(bill_amt);
  }

  @Test
  public void getDiscountTypeTest() throws Exception {

    String discount_type = employeePriceCalculationStrategy.getDiscountType();
    assertThat(discount_type).isEqualTo("EMPLOYEE");

  }
}
