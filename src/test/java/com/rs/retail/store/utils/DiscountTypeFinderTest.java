package com.rs.retail.store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.rs.retail.store.dao.CustomerRepository;
import com.rs.retail.store.domain.Customer;
import com.rs.retail.store.domain.CustomerType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DiscountTypeFinderTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private DiscountTypeFinder discountTypeFinder;

  @Test
  public void findDiscountActionForBillingTest_Employee(){

    String employee = "EMPLOYEE";
    Customer customer = new Customer();
    customer.setCustomerType(CustomerType.EMPLOYEE);
    Mockito.when(customerRepository.findByCustomerName(employee)).thenReturn(customer);

    String customer_type = discountTypeFinder.findDiscountActionForBilling(employee);

    assertThat(customer_type).isEqualTo(employee);
  }

  @Test
  public void findDiscountActionForBillingTest_AFFILIATE(){

    String affiliate = "AFFILIATE";
    Customer customer = new Customer();
    customer.setCustomerType(CustomerType.AFFILIATE);
    Mockito.when(customerRepository.findByCustomerName(affiliate)).thenReturn(customer);

    String customer_type = discountTypeFinder.findDiscountActionForBilling(affiliate);

    assertThat(customer_type).isEqualTo(affiliate);
  }

  @Test
  public void findDiscountActionForBillingTest_LONG(){

    String longCustomer = "LONG";
    Customer customer = new Customer();
    Mockito.when(customerRepository.findByCustomerName(longCustomer)).thenReturn(customer);
    Mockito.when(customerRepository.findByCustomerNameAndAndServiceLengthIsGreaterThan(longCustomer, 2)).thenReturn(customer);

    String customer_type = discountTypeFinder.findDiscountActionForBilling(longCustomer);

    assertThat(customer_type).isEqualTo(longCustomer);
  }

  @Test
  public void findDiscountActionForBillingTest_FLAT(){

    String flat = "FLAT";
    Customer customer = new Customer();
    Mockito.when(customerRepository.findByCustomerName(flat)).thenReturn(customer);
    Mockito.when(customerRepository.findByCustomerNameAndAndServiceLengthIsGreaterThan(flat, 2)).thenReturn(null);

    String customer_type = discountTypeFinder.findDiscountActionForBilling(flat);

    assertThat(customer_type).isEqualTo(flat);
  }

}
