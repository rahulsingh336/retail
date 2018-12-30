package com.rs.retail.store.dao;

import com.rs.retail.store.domain.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

  Customer findByCustomerName(String customerName);
  Customer findByCustomerNameAndAndServiceLengthIsGreaterThan(String customerName, long serviceLength);

}
