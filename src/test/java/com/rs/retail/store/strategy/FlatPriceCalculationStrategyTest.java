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
public class FlatPriceCalculationStrategyTest {


  private static final String MOCK = "Mock";
  private static final String MOCK_ITEM = "MOCK_ITEM";
  private static final BigDecimal ITEM_PRICE = new BigDecimal(36.00);
  private FlatPriceCalculationStrategy flatPriceCalculationStrategy;


  @Before
  public void setUp() throws Exception {
   flatPriceCalculationStrategy = new FlatPriceCalculationStrategy();
  }

  @Test
  public void calculatePriceTest() throws Exception {

    BillingRequest billingDto = new BillingRequest();
    Item item = new Item();
    item.setItemPrice(ITEM_PRICE);
    item.setItemType(ItemType.GROCERY);
    item.setItemName(MOCK_ITEM);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingDto.setCustomerName(MOCK);
    billingDto.setItems(itemList);

    BigDecimal return_value = flatPriceCalculationStrategy.calculatePrice(billingDto);

    BigDecimal bill_amt = ITEM_PRICE.setScale(2, BigDecimal.ROUND_HALF_UP);
    assertThat(return_value).isEqualTo(bill_amt);

  }

  @Test
  public void calculatePriceTest_1_Hundred_Amount() throws Exception {

    BillingRequest billingDto = new BillingRequest();
    Item item = new Item();
    BigDecimal itemPrice = new BigDecimal(100.00);
    item.setItemPrice(itemPrice);
    item.setItemType(ItemType.GROCERY);
    item.setItemName(MOCK_ITEM);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingDto.setCustomerName(MOCK);
    billingDto.setItems(itemList);

    BigDecimal return_value = flatPriceCalculationStrategy.calculatePrice(billingDto);

    BigDecimal bill_amt = itemPrice.subtract(new BigDecimal(5)).setScale(2, BigDecimal.ROUND_HALF_UP);
    assertThat(return_value).isEqualTo(bill_amt);

  }

  @Test
  public void calculatePriceTest_990_Amount() throws Exception {

    BillingRequest billingDto = new BillingRequest();
    Item item = new Item();
    BigDecimal itemPrice = new BigDecimal(990.00);
    item.setItemPrice(itemPrice);
    item.setItemType(ItemType.GROCERY);
    item.setItemName(MOCK_ITEM);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingDto.setCustomerName(MOCK);
    billingDto.setItems(itemList);

    BigDecimal return_value = flatPriceCalculationStrategy.calculatePrice(billingDto);

    BigDecimal bill_amt = itemPrice.subtract(new BigDecimal(45)).setScale(2, BigDecimal.ROUND_HALF_UP);
    assertThat(return_value).isEqualTo(bill_amt);

  }

  @Test
  public void getDiscountTest() throws Exception {

    BigDecimal discount = flatPriceCalculationStrategy.getDiscount();
    BigDecimal expected = new BigDecimal(5);
    assertThat(discount).isEqualTo(expected);

  }

  @Test
  public void getDiscountTypeTest() throws Exception {

    String discount_type = flatPriceCalculationStrategy.getDiscountType();
    assertThat(discount_type).isEqualTo("FLAT");

  }
}
