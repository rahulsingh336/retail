package com.rs.retail.store.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rs.retail.store.command.BillingRequest;
import com.rs.retail.store.exception.CustomerIsNullException;
import com.rs.retail.store.exception.ItemNotFoundException;
import com.rs.retail.store.service.RetailStoreCalculationService;
import com.rs.retail.store.utils.validator.RequestValidator;
import com.rs.retail.store.utils.validator.RequestValidatorTest;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by e076103 on 19-12-2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BillingController.class)
public class BillingControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private RequestValidator requestValidator;

  @MockBean
  private RetailStoreCalculationService retailStoreCalculationService;

  @Test
  public void getBillAmount_success()
      throws Exception {

    String billingRequest = populateData();
    BillingRequest billingReq = new BillingRequest();
    billingReq.setBillAmount(new BigDecimal(36));

    Mockito.doNothing().when(requestValidator).validate(any());
    when(retailStoreCalculationService.calculateBillPrice(any())).thenReturn(billingReq);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/bill").content(billingRequest)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    mvc.perform(requestBuilder)
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.billAmount", is(36)));
  }

  @Test
  public void getBillAmount_CustomerNotInRequest()
      throws Exception {

    String billingRequest = populateCustomerNotFound();
    BillingRequest billingReq = new BillingRequest();
    billingReq.setBillAmount(new BigDecimal(36));

    Mockito.doThrow(new CustomerIsNullException("customer not found in request"))
        .when(requestValidator).validate(any(BillingRequest.class));

    when(retailStoreCalculationService.calculateBillPrice(any())).thenReturn(billingReq);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/bill").content(billingRequest)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    mvc.perform(requestBuilder)
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void getBillAmount_ItemNotFoundExceptionInRequest()
      throws Exception {

    String billingRequest = populateItemNotPresent();
    BillingRequest billingReq = new BillingRequest();
    billingReq.setBillAmount(new BigDecimal(36));

    Mockito.doThrow(new ItemNotFoundException("Item not present"))
        .when(requestValidator).validate(any(BillingRequest.class));

    when(retailStoreCalculationService.calculateBillPrice(any())).thenReturn(billingReq);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/bill").content(billingRequest)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    mvc.perform(requestBuilder)
        .andExpect(status().is4xxClientError());
  }

  private String populateData() {
    String success_data = "{\n"
        + " \"customerName\": \"Ram\",\n"
        + " \"items\" : [ {\n"
        + "    \"itemName\" : \"Sugar\"\n"
        + "  }\n"
        + "  ]\n"
        + "}";
    return success_data;
  }

  private String populateCustomerNotFound() {
    String data = "{\n"
        + " \"items\" : [ {\n"
        + "    \"itemName\" : \"Sugar\"\n"
        + "  }\n"
        + "  ]\n"
        + "}";
    return data;
  }

  private String populateItemNotPresent() {
    String data = "{\n"
        + "    \"customerName\": \"Ram\"\n"
        + "    }";
    return data;
  }

}
