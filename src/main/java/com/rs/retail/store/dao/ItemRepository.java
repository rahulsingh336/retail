package com.rs.retail.store.dao;

import com.rs.retail.store.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long>{

 Item findByItemName(String itemName);


}
