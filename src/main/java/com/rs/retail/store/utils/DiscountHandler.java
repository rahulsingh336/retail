package com.rs.retail.store.utils;

import com.rs.retail.store.strategy.PriceCalculationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DiscountHandler {

  @Autowired
  private List<PriceCalculationStrategy> priceCalculationStrategyList;
  private Map<String, PriceCalculationStrategy> priceHandlers;

  @PostConstruct
  public void buildDiscountHandlers(){
    priceHandlers = new HashMap<>();
    priceCalculationStrategyList.forEach(priceCalculationStrategy ->
      priceHandlers.put(priceCalculationStrategy.getDiscountType(), priceCalculationStrategy));
  }

  public PriceCalculationStrategy getDiscounthandler(String discountType){
    //handler if key not found, can it ever occur
    return priceHandlers.get(discountType);
  }


}
