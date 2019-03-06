package com.rs.retail.store.controller;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.RestAssured;
import com.rs.retail.store.RetailStoreApplication;
import com.rs.retail.store.dao.CharacterRepository;
import com.rs.retail.store.domain.Character;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringBootTest(classes = RetailStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)   // 2
public class CharacterControllerTest {

  @Autowired   //
  CharacterRepository repository;

  Character mickey;
  Character minnie;
  Character pluto;

  @Value("${local.server.port}")   // 6
      int port;

  @Before
  public void setUp() {
    // 7
    mickey = new Character("Mickey Mouse");
    minnie = new Character("Minnie Mouse");
    pluto = new Character("Pluto");

    // 8
    repository.deleteAll();
    repository.save(mickey);
    repository.save(minnie);
    repository.save(pluto);

    //9
    RestAssured.port = port;

  }
  // 10
  /*@Test
  public void canFetchMickey() {
    Integer mickeyId = mickey.getId();

    given().when().
        get("/characters/{id}", mickeyId).
        then().
        statusCode(HttpStatus.SC_OK).
        body("name", Matchers.is("Mickey Mouse")).
        body("id", Matchers.is(mickeyId));
  }
*/
  @Test
  public void canFetchAll() {
    given().when().
        get("/characters").
        then().
        statusCode(HttpStatus.SC_OK).
        body("name", Matchers.hasItems("Mickey Mouse", "Minnie Mouse", "Pluto"));
  }

  @Test
  public void canDeletePluto() {
    Integer plutoId = pluto.getId();

    given().when()
        .delete("/characters/{id}", plutoId).
        then().
        statusCode(HttpStatus.SC_NO_CONTENT);
  }
}
