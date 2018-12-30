package com.rs.retail.store.utils.validator;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.dao.ItemRepository;
import com.rs.retail.store.domain.Item;
import com.rs.retail.store.exception.CustomerIsNullException;
import com.rs.retail.store.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestValidator {

  @Autowired
  private ItemRepository itemRepository;

  public void validate(BillingRequest billingRequest) {
    validateCustomer(billingRequest);
    validateItems(billingRequest);
  }

  private void validateCustomer(BillingRequest billingRequest) {
    if (null == billingRequest.getCustomerName()) {
      throw new CustomerIsNullException(
          "Customer is empty");
    }
  }

  private void validateItems(BillingRequest billingRequest) {
    if (null == billingRequest.getItems() || billingRequest.getItems().isEmpty()){
      throw new ItemNotFoundException("items not found in request");
    }
    billingRequest.getItems().forEach(item -> {
      //validation, may need to cache service
      Item itemRetrived = itemRepository.findByItemName(item.getItemName());
      if (null == itemRetrived) {
        throw new ItemNotFoundException("item not found - " + item.getItemName());
      }
    });
  }
}