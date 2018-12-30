package com.rs.retail.store.utils.validator;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.dao.ItemRepository;
import com.rs.retail.store.domain.Item;
import com.rs.retail.store.domain.ItemType;
import com.rs.retail.store.exception.CustomerIsNullException;
import com.rs.retail.store.exception.ItemNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RequestValidatorTest {

  private static final String MOCK_CUSTOMER = "mock_customer";
  private static final String MOCK_ITEM = "mock_item";

  @Mock
  private ItemRepository itemRepository;

  @InjectMocks
  private RequestValidator requestValidator;

  @Test
  public void validate_no_error() {
    Item item = new Item();
    item.setItemName(MOCK_ITEM);
    item.setItemType(ItemType.GROCERY);
    item.setItemPrice(new BigDecimal(1.0));
    BillingRequest billingRequest = new BillingRequest();
    billingRequest.setCustomerName(MOCK_CUSTOMER);
    List<Item> itemList = new ArrayList<>();
    itemList.add(item);
    billingRequest.setItems(itemList);

    Mockito.when(itemRepository.findByItemName(MOCK_ITEM)).thenReturn(item);

    requestValidator.validate(billingRequest);
  }

  @Test(expected = CustomerIsNullException.class)
  public void validate_no_customer() {
    BillingRequest billingRequest = new BillingRequest();

    requestValidator.validate(billingRequest);
  }

  @Test(expected = ItemNotFoundException.class)
  public void validate_no_item_in_db() {
    BillingRequest billingRequest = new BillingRequest();
    billingRequest.setCustomerName(MOCK_CUSTOMER);

    Mockito.when(itemRepository.findByItemName(MOCK_ITEM)).thenReturn(null);

    requestValidator.validate(billingRequest);
  }
}