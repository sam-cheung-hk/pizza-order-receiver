package com.challenge.order.receiver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_CreateOrder_Success() throws Exception {
        JsonMapper jsonMapper = new JsonMapper();

        ObjectNode pizzaDetail = jsonMapper.createObjectNode();
        pizzaDetail.put("name", "PIZZA_A");
        pizzaDetail.put("quantity", 1);
        pizzaDetail.put("price", 100);

        ObjectNode pizzaOrder = jsonMapper.createObjectNode();
        pizzaOrder.put("orderReferenceNo", "784690331588403300");
        pizzaOrder.put("orderTime", "2021-12-05T16:00:00+08:00");

        ArrayNode pizzaDetails = pizzaOrder.putArray("orderDetails");
        pizzaDetails.add(pizzaDetail);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(pizzaOrder));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);

        JsonNode responseBody = jsonMapper.readTree(result.getResponse().getContentAsString());

        Assert.assertNotNull(responseBody.get("orderReferenceNo"));
        Assert.assertNotNull(responseBody.get("orderTime"));
        Assert.assertNotNull(responseBody.get("orderTransNo"));
        Assert.assertNotNull(responseBody.get("totalPrice"));
        Assert.assertEquals(responseBody.get("orderReferenceNo").asText(), "784690331588403300");
        Assert.assertEquals(responseBody.get("totalPrice").asInt(), 100);
        Assert.assertEquals(ZonedDateTime.parse(responseBody.get("orderTime").asText()).toEpochSecond(), ZonedDateTime.parse("2021-12-05T16:00:00+08:00").toEpochSecond());
    }

    @Test
    public void test_CreateOrder_Failure_Bad_Request() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);

        JsonMapper jsonMapper = new JsonMapper();
        JsonNode responseBody = jsonMapper.readTree(result.getResponse().getContentAsString());

        Assert.assertNotNull(responseBody.get("errorCode"));
        Assert.assertEquals(responseBody.get("errorCode").asText(), "E0001_400_0001");
    }

}
