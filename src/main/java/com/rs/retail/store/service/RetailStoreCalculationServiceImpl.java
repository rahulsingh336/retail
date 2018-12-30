package com.rs.retail.store.service;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.dao.ItemRepository;
import com.rs.retail.store.domain.Item;
import com.rs.retail.store.strategy.PriceCalculationStrategy;
import com.rs.retail.store.utils.DiscountHandler;
import com.rs.retail.store.utils.DiscountTypeFinder;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetailStoreCalculationServiceImpl implements RetailStoreCalculationService {

  @Autowired
  private DiscountTypeFinder discountTypeFinder;

  @Autowired
  private DiscountHandler discountHandler;

  @Autowired
  private ItemRepository itemRepository;

  @Override
  public BillingRequest calculateBillPrice(BillingRequest billingDto) {
    String discountActionType = discountTypeFinder.findDiscountActionForBilling(billingDto.getCustomerName());
    setItemFields(billingDto);
    PriceCalculationStrategy calculationStrategy = discountHandler.getDiscounthandler(discountActionType);
    BigDecimal billAmount = calculationStrategy.calculatePrice(billingDto);
    billingDto.setBillAmount(billAmount);
    return billingDto;
  }

  private void setItemFields(BillingRequest billingDto) {
    billingDto.getItems().forEach(item -> {
      Item fetchedItem = itemRepository.findByItemName(item.getItemName());
      item.setItemType(fetchedItem.getItemType());
      item.setItemPrice(fetchedItem.getItemPrice());
    });
  }

}
