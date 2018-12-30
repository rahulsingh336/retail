package com.rs.retail.store.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Bill {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  private Customer customer;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Item> items;

}
