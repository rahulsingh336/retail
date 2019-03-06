package com.rs.retail.store.controller;

import com.rs.retail.store.dao.CharacterRepository;
import com.rs.retail.store.domain.Character;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CharacterController {

  @Autowired
  private CharacterRepository repository;

  @RequestMapping("/characters")
  List<Character> characters() {
    return repository.findAll();
  }

  @RequestMapping(value = "/characters/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void delete(@PathVariable("id") Integer id) {
    repository.deleteById(id);
  }

  // more controller methods
}