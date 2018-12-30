package com.rs.retail.store.utils;

import static com.rs.retail.store.common.ApplicationConstants.AFFILIATE;
import static com.rs.retail.store.common.ApplicationConstants.EMPLOYEE;
import static com.rs.retail.store.common.ApplicationConstants.FLAT;
import static com.rs.retail.store.common.ApplicationConstants.LONG;

import com.rs.retail.store.dao.CustomerRepository;
import com.rs.retail.store.domain.Customer;
import com.rs.retail.store.domain.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountTypeFinder {

  @Autowired
  private CustomerRepository customerRepository;

  public String findDiscountActionForBilling(String customerName){

    Customer customer = customerRepository.findByCustomerName(customerName);

    if(null != customer && null != customer.getCustomerType() && CustomerType.EMPLOYEE.name().equals(customer.getCustomerType().name())){
      return EMPLOYEE;
    }else if (null != customer && null != customer.getCustomerType() && CustomerType.AFFILIATE.name().equals(customer.getCustomerType().name())){
      return AFFILIATE;
    }else if(checkIfLongCustomer(customerName)) {
      return LONG;
    }else {
      return FLAT;
    }
  }

  private boolean checkIfLongCustomer(String customerName) {
    //make service length dynamic
    return null != customerRepository
        .findByCustomerNameAndAndServiceLengthIsGreaterThan(customerName, 2);
  }

}
