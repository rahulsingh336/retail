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
public class LongServicePriceCalculationStrategyTest {

  private static final String MOCK = "Mock";
  private static final String MOCK_ITEM = "MOCK_ITEM";
  private static final BigDecimal ITEM_PRICE = new BigDecimal(100.00);

  private LongServicePriceCalculationStrategy longServicePriceCalculationStrategy;

  @Before
  public void setUp() throws Exception {
    longServicePriceCalculationStrategy = new LongServicePriceCalculationStrategy();
  }

  @Test
  public void calculatePriceTest() throws Exception {
    BillingRequest billingDto = new BillingRequest();
    Item item = new Item();
    item.setItemPrice(ITEM_PRICE);
    item.setItemName(MOCK_ITEM);
    item.setItemType(ItemType.NONGROCERY);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingDto.setCustomerName(MOCK);
    billingDto.setItems(itemList);

    BigDecimal return_value =  longServicePriceCalculationStrategy.calculatePrice(billingDto);

    BigDecimal bill_amt = new BigDecimal(95.00).setScale(2, BigDecimal.ROUND_HALF_UP);
    assertThat(return_value).isEqualTo(bill_amt);

  }

  @Test
  public void getDiscountTypeTest() throws Exception {

    String discount_type = longServicePriceCalculationStrategy.getDiscountType();
    assertThat(discount_type).isEqualTo("LONG");

  }
}
