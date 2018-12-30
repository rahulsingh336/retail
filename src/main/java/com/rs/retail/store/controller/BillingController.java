package com.rs.retail.store.controller;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.command.BillingResponse;
import com.rs.retail.store.service.RetailStoreCalculationService;
import com.rs.retail.store.utils.validator.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BillingController {

  @Autowired
  private RequestValidator requestValidator;

  @Autowired
  private RetailStoreCalculationService retailStoreCalculationService;

  @PostMapping("/bill")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<BillingResponse> postBill(@RequestBody BillingRequest billingRequest) {
    requestValidator.validate(billingRequest);
    BillingRequest billCalculated = retailStoreCalculationService.calculateBillPrice(billingRequest);
    BillingResponse billingResponse = new BillingResponse(billCalculated.getBillAmount(),billCalculated.getItems(), billCalculated.getCustomerName());
    return new ResponseEntity<>(billingResponse, HttpStatus.ACCEPTED);
  }

}
