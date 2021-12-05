package com.challenge.order.receiver.converter;

import com.challenge.order.receiver.bean.db.PizzaOrder;
import com.challenge.order.receiver.bean.db.PizzaOrderDetail;
import com.challenge.order.receiver.bean.incoming.request.CreateOrderRequest;
import com.challenge.order.receiver.bean.incoming.response.CreateOrderResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaOrderDataConverterTests {

    @Autowired
    private PizzaOrderDataConverter pizzaOrderDataConverter;

    @Test
    public void test_TransformCreateOrderRequestToPizzaOrder() {
        ZonedDateTime orderTime1 = ZonedDateTime.now();

        // Test 1

        List<CreateOrderRequest.OrderDetail> request1OrderDetails = new ArrayList<>();

        CreateOrderRequest.OrderDetail request1OrderDetail1 = new CreateOrderRequest.OrderDetail();
        request1OrderDetail1.setName("PIZZA_A");
        request1OrderDetail1.setQuantity(1);
        request1OrderDetail1.setPrice(BigDecimal.valueOf(100L));
        request1OrderDetails.add(request1OrderDetail1);

        CreateOrderRequest request1 = new CreateOrderRequest();
        request1.setOrderReferenceNo("784690331588403205");
        request1.setOrderTime(orderTime1);
        request1.setOrderDetails(request1OrderDetails);

        PizzaOrder pizzaOrder1 = pizzaOrderDataConverter.transformToPizzaOrder(request1);

        Assert.assertNotNull(pizzaOrder1);

        Assert.assertEquals(pizzaOrder1.getOrderReferenceNumber(), "784690331588403205");
        Assert.assertEquals(pizzaOrder1.getOrderTime(), orderTime1);
        Assert.assertEquals(pizzaOrder1.getOrderDetails().size(), 1);

        PizzaOrderDetail pizzaOrder1Detail1 = pizzaOrder1.getOrderDetails().get(0);
        Assert.assertEquals(pizzaOrder1Detail1.getName(), "PIZZA_A");
        Assert.assertEquals(pizzaOrder1Detail1.getQuantity().intValue(), 1);
        Assert.assertEquals(pizzaOrder1Detail1.getPrice().intValue(), 100);

        // Test 2

        ZonedDateTime orderTime2 = ZonedDateTime.now();

        List<CreateOrderRequest.OrderDetail> request2OrderDetails = new ArrayList<>();

        CreateOrderRequest.OrderDetail request2OrderDetail1 = new CreateOrderRequest.OrderDetail();
        request2OrderDetail1.setName("PIZZA_A");
        request2OrderDetail1.setQuantity(1);
        request2OrderDetail1.setPrice(BigDecimal.valueOf(100L));
        request2OrderDetails.add(request2OrderDetail1);

        CreateOrderRequest.OrderDetail request2OrderDetail2 = new CreateOrderRequest.OrderDetail();
        request2OrderDetail2.setName("PIZZA_B");
        request2OrderDetail2.setQuantity(2);
        request2OrderDetail2.setPrice(BigDecimal.valueOf(200L));
        request2OrderDetails.add(request2OrderDetail2);

        CreateOrderRequest request2 = new CreateOrderRequest();
        request2.setOrderReferenceNo("784690331588403206");
        request2.setOrderTime(orderTime2);
        request2.setOrderDetails(request2OrderDetails);

        PizzaOrder pizzaOrder2 = pizzaOrderDataConverter.transformToPizzaOrder(request2);

        Assert.assertNotNull(pizzaOrder2);

        Assert.assertEquals(pizzaOrder2.getOrderReferenceNumber(), "784690331588403206");
        Assert.assertEquals(pizzaOrder2.getOrderTime(), orderTime2);
        Assert.assertEquals(pizzaOrder2.getOrderDetails().size(), 2);

        PizzaOrderDetail pizzaOrder2Detail1 = pizzaOrder2.getOrderDetails().get(0);
        Assert.assertEquals(pizzaOrder2Detail1.getName(), "PIZZA_A");
        Assert.assertEquals(pizzaOrder2Detail1.getQuantity().intValue(), 1);
        Assert.assertEquals(pizzaOrder2Detail1.getPrice().intValue(), 100);

        PizzaOrderDetail pizzaOrder2Detail2 = pizzaOrder2.getOrderDetails().get(1);
        Assert.assertEquals(pizzaOrder2Detail2.getName(), "PIZZA_B");
        Assert.assertEquals(pizzaOrder2Detail2.getQuantity().intValue(), 2);
        Assert.assertEquals(pizzaOrder2Detail2.getPrice().intValue(), 200);
    }

    @Test
    public void test_TransformPizzaOrderToCreateOrderResponse() {
        ZonedDateTime orderTime = ZonedDateTime.now();

        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setId("784690331588403300");
        pizzaOrder.setOrderReferenceNumber("784690331588403205");
        pizzaOrder.setOrderTime(orderTime);
        pizzaOrder.setTotalPrice(BigDecimal.valueOf(100L));

        CreateOrderResponse response = pizzaOrderDataConverter.transformToCreateOrderResponse(pizzaOrder);

        Assert.assertNotNull(response);

        Assert.assertEquals(response.getOrderTransNo(), "784690331588403300");
        Assert.assertEquals(response.getOrderReferenceNo(), "784690331588403205");
        Assert.assertEquals(response.getOrderTime(), orderTime);
        Assert.assertEquals(response.getTotalPrice(), BigDecimal.valueOf(100L).setScale(2, RoundingMode.HALF_EVEN));
    }

}
