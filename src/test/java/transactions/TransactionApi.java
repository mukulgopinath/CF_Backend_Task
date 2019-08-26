package transactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import transactions.model.TransactionModel;


public class TransactionApi extends CurrencyFairApplicationTests {
   @Override
   @Before
   public void setUp() {
      super.setUp();
   }
   @Test
   public void getProductsList() throws Exception {
      String uri = "/getAllTransactions";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      TransactionModel[] productlist = super.mapFromJson(content, TransactionModel[].class);
      assertTrue(productlist.length > 0);
   }
   @Test
   public void addTransaction() throws Exception {
      String uri = "/products";
      TransactionModel model = new TransactionModel();
      model.setUserId("3000");
      model.setAmountBuy("1000");
      model.setAmountSell("797.10");
      model.setCurrencyFrom("GBP");
      model.setCurrencyTo("EUR");
      model.setOriginatingCountry("US");
      model.setRate("0.7971");
      model.setTimePlaced("01-JAN-10 00:00:00");
      String inputJson = super.mapToJson(model);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(201, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "Transaction has been added successfully");
   }
   
}

/*
package restapi;


import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;


import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionApi{
	@LocalServerPort
    int randomServerPort;
     
	@Test
	public void testGetEmployeeListSuccess() throws URISyntaxException {
		try {
			RestTemplate restTemplate = new RestTemplate();
		    final String baseUrl = "http://localhost:"+randomServerPort+"/addTransaction/";
		    URI uri = new URI(baseUrl);
		    JSONObject jsonobj = new JSONObject();
		    jsonobj.put("userId","1290");
		    jsonobj.put("currencyFrom","EUR");
		    jsonobj.put("currencyTo","GBP");
		    jsonobj.put("amountSell",1000);
		    jsonobj.put("amountBuy",747.10);
		    jsonobj.put("rate",0.7471);
		    jsonobj.put("timePlaced","22-SEP-19 07:30:00");
		    jsonobj.put("originatingCountry","US");
	
//			TransactionModel model = new TransactionModel(jsonobj);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<JSONObject> request = new HttpEntity<>(jsonobj, headers);
		    ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
		    Assert.assertEquals(true, !result.getBody().contains("error"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/