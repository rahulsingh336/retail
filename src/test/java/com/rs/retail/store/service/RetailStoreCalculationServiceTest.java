package com.rs.retail.store.service;

import static com.rs.retail.store.common.ApplicationConstants.FLAT;
import static org.assertj.core.api.Assertions.assertThat;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.dao.ItemRepository;
import com.rs.retail.store.domain.Item;
import com.rs.retail.store.domain.ItemType;
import com.rs.retail.store.strategy.PriceCalculationStrategy;
import com.rs.retail.store.utils.DiscountHandler;
import com.rs.retail.store.utils.DiscountTypeFinder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by e076103 on 19-12-2018.
 */
@RunWith(SpringRunner.class)
public class RetailStoreCalculationServiceTest {

  private static final String MOCK = "Mock";
  private static final String MOCK_ITEM = "MOCK_ITEM";
  private static final BigDecimal BILL_AMOUNT = new BigDecimal(336);

  @Mock
  private DiscountTypeFinder discountTypeFinder;

  @Mock
  private DiscountHandler discountHandler;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private PriceCalculationStrategy priceCalculationStrategy;

  @InjectMocks
  private RetailStoreCalculationServiceImpl retailStoreCalculationService;

  @Test
  public void calculateBillPriceTest() throws Exception {
    BillingRequest billingDto = new BillingRequest();
    Item item = new Item();
    item.setItemPrice(new BigDecimal(36));
    item.setItemType(ItemType.GROCERY);
    item.setItemName(MOCK_ITEM);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingDto.setCustomerName(MOCK);
    billingDto.setItems(itemList);

    Mockito.when(discountTypeFinder.findDiscountActionForBilling(billingDto.getCustomerName())).thenReturn(FLAT);
    Mockito.when(itemRepository.findByItemName(MOCK_ITEM)).thenReturn(item);
    Mockito.when(discountHandler.getDiscounthandler(FLAT)).thenReturn(priceCalculationStrategy);
    Mockito.when(priceCalculationStrategy.calculatePrice(billingDto)).thenReturn(
        BILL_AMOUNT);

    BillingRequest bill_amt_calculated = retailStoreCalculationService.calculateBillPrice(billingDto);

    assertThat(bill_amt_calculated.getBillAmount()).isEqualTo(BILL_AMOUNT);

  }
}
