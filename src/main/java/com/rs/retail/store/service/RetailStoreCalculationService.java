package com.rs.retail.store.service;

import com.rs.retail.store.command.BillingRequest;

@FunctionalInterface
public interface RetailStoreCalculationService {

  BillingRequest calculateBillPrice(BillingRequest billingDto);

}
