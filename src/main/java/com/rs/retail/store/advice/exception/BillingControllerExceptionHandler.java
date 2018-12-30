package com.rs.retail.store.advice.exception;

import com.rs.retail.store.exception.CustomerIsNullException;
import com.rs.retail.store.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BillingControllerExceptionHandler {

  @ExceptionHandler(CustomerIsNullException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void handleCustomerNullException(CustomerIsNullException exception) {
    log.error("customer is not present {}", exception.getMessage());
  }

  @ExceptionHandler(ItemNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void handleItemNotFoundException(ItemNotFoundException exception) {
    log.error("Item is not present {}", exception.getMessage());
  }

}
