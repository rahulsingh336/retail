package com.rs.retail.store.exception;

public class ItemNotFoundException extends RuntimeException{

  public ItemNotFoundException(String message) {
    super(message);
  }
}
