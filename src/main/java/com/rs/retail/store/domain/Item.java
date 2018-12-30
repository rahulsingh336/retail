package com.rs.retail.store.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Item {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String itemName;

  @Column
  private BigDecimal itemPrice;

  @Enumerated(EnumType.STRING)
  @Column
  private ItemType itemType;

}
