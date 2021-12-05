package com.challenge.order.receiver.service;

import com.challenge.order.receiver.bean.db.PizzaOrder;
import com.challenge.order.receiver.bean.db.PizzaOrderDetail;
import com.challenge.order.receiver.repository.PizzaOrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaOrderServiceTests {

    @Autowired
    private PizzaOrderService pizzaOrderService;

    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;

    @Test
    @Transactional
    public void test_RecordPizzaOrder_New_SingleDetail() {
        ZonedDateTime orderTime = ZonedDateTime.now();

        PizzaOrderDetail orderDetail = new PizzaOrderDetail();
        orderDetail.setName("PIZZA_A");
        orderDetail.setQuantity(1);
        orderDetail.setPrice(BigDecimal.valueOf(100L));

        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setOrderReferenceNumber("784690331588403206");
        pizzaOrder.setOrderTime(orderTime);

        orderDetail.setPizzaOrder(pizzaOrder);
        pizzaOrder.addOrderDetail(orderDetail);

        PizzaOrder pizzaOrderAfterSave = pizzaOrderService.recordPizzaOrder(pizzaOrder);

        Assert.assertNotNull(pizzaOrderAfterSave);

        Assert.assertNotNull(pizzaOrderAfterSave.getId());
        Assert.assertEquals(pizzaOrderAfterSave.getOrderReferenceNumber(), "784690331588403206");
        Assert.assertEquals(pizzaOrderAfterSave.getOrderTime(), orderTime);

        pizzaOrderRepository.flush();

        PizzaOrder pizzaOrderDb = pizzaOrderRepository.getById(pizzaOrderAfterSave.getId());

        Assert.assertNotNull(pizzaOrderDb);

        Assert.assertEquals(pizzaOrderDb.getOrderReferenceNumber(), "784690331588403206");
        Assert.assertEquals(pizzaOrderDb.getOrderTime(), orderTime);
        Assert.assertEquals(pizzaOrderDb.getTotalPrice().intValue(), 100);
        Assert.assertNotNull(pizzaOrderDb.getCreationTime());
        Assert.assertNotNull(pizzaOrderDb.getModificationTime());

        Assert.assertEquals(pizzaOrderDb.getOrderDetails().size(), 1);
        PizzaOrderDetail orderDetailDb = pizzaOrderDb.getOrderDetails().get(0);

        Assert.assertNotNull(orderDetailDb.getId());
        Assert.assertEquals(orderDetailDb.getPizzaOrder(), pizzaOrderDb);
        Assert.assertEquals(orderDetailDb.getName(), "PIZZA_A");
        Assert.assertEquals(orderDetailDb.getQuantity().intValue(), 1);
        Assert.assertEquals(orderDetailDb.getPrice().intValue(), 100);
        Assert.assertNotNull(orderDetailDb.getCreationTime());
        Assert.assertNotNull(orderDetailDb.getModificationTime());
    }

    @Test
    @Transactional
    public void test_RecordPizzaOrder_New_TwoDetails() {
        ZonedDateTime orderTime = ZonedDateTime.now();

        PizzaOrderDetail orderDetail1 = new PizzaOrderDetail();
        orderDetail1.setName("PIZZA_A");
        orderDetail1.setQuantity(1);
        orderDetail1.setPrice(BigDecimal.valueOf(100L));

        PizzaOrderDetail orderDetail2 = new PizzaOrderDetail();
        orderDetail2.setName("PIZZA_B");
        orderDetail2.setQuantity(2);
        orderDetail2.setPrice(BigDecimal.valueOf(200L));

        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setOrderReferenceNumber("784690331588403207");
        pizzaOrder.setOrderTime(orderTime);

        orderDetail1.setPizzaOrder(pizzaOrder);
        pizzaOrder.addOrderDetail(orderDetail1);

        orderDetail2.setPizzaOrder(pizzaOrder);
        pizzaOrder.addOrderDetail(orderDetail2);

        PizzaOrder pizzaOrderAfterSave = pizzaOrderService.recordPizzaOrder(pizzaOrder);

        Assert.assertNotNull(pizzaOrderAfterSave);

        Assert.assertNotNull(pizzaOrderAfterSave.getId());
        Assert.assertEquals(pizzaOrderAfterSave.getOrderReferenceNumber(), "784690331588403207");
        Assert.assertEquals(pizzaOrderAfterSave.getOrderTime(), orderTime);

        pizzaOrderRepository.flush();

        PizzaOrder pizzaOrderDb = pizzaOrderRepository.getById(pizzaOrderAfterSave.getId());

        Assert.assertNotNull(pizzaOrderDb);

        Assert.assertEquals(pizzaOrderDb.getOrderReferenceNumber(), "784690331588403207");
        Assert.assertEquals(pizzaOrderDb.getOrderTime(), orderTime);
        Assert.assertEquals(pizzaOrderDb.getTotalPrice().intValue(), 300);
        Assert.assertNotNull(pizzaOrderDb.getCreationTime());
        Assert.assertNotNull(pizzaOrderDb.getModificationTime());

        Assert.assertEquals(pizzaOrderDb.getOrderDetails().size(), 2);
        for (PizzaOrderDetail orderDetailDb : pizzaOrderDb.getOrderDetails()) {
            Assert.assertTrue("PIZZA_A".equals(orderDetailDb.getName()) || "PIZZA_B".equals(orderDetailDb.getName()));
            if ("PIZZA_A".equals(orderDetailDb.getName())) {
                // Pizza A
                Assert.assertNotNull(orderDetailDb.getId());
                Assert.assertEquals(orderDetailDb.getPizzaOrder(), pizzaOrderDb);
                Assert.assertEquals(orderDetailDb.getQuantity().intValue(), 1);
                Assert.assertEquals(orderDetailDb.getPrice().intValue(), 100);
                Assert.assertNotNull(orderDetailDb.getCreationTime());
                Assert.assertNotNull(orderDetailDb.getModificationTime());
            } else {
                // Pizza B
                Assert.assertNotNull(orderDetailDb.getId());
                Assert.assertEquals(orderDetailDb.getPizzaOrder(), pizzaOrderDb);
                Assert.assertEquals(orderDetailDb.getQuantity().intValue(), 2);
                Assert.assertEquals(orderDetailDb.getPrice().intValue(), 200);
                Assert.assertNotNull(orderDetailDb.getCreationTime());
                Assert.assertNotNull(orderDetailDb.getModificationTime());
            }
        }
    }

    @Test
    @Transactional
    public void test_RecordPizzaOrder_Duplicated_ReferenceNo() {
        ZonedDateTime orderTime = ZonedDateTime.now();

        PizzaOrderDetail orderDetail = new PizzaOrderDetail();
        orderDetail.setName("PIZZA_A");
        orderDetail.setQuantity(1);
        orderDetail.setPrice(BigDecimal.valueOf(100L));

        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setOrderReferenceNumber("784690331588403205");
        pizzaOrder.setOrderTime(orderTime);

        orderDetail.setPizzaOrder(pizzaOrder);
        pizzaOrder.addOrderDetail(orderDetail);

        PizzaOrder pizzaOrderAfterSave = pizzaOrderService.recordPizzaOrder(pizzaOrder);

        Assert.assertNotNull(pizzaOrderAfterSave);

        Assert.assertNotNull(pizzaOrderAfterSave.getId());
        Assert.assertEquals(pizzaOrderAfterSave.getOrderReferenceNumber(), "784690331588403205");

        long recordCount = pizzaOrderRepository.count();

        PizzaOrderDetail orderDetail2 = new PizzaOrderDetail();
        orderDetail2.setName("PIZZA_A");
        orderDetail2.setQuantity(1);
        orderDetail2.setPrice(BigDecimal.valueOf(100L));

        PizzaOrder pizzaOrder2 = new PizzaOrder();
        pizzaOrder2.setOrderReferenceNumber("784690331588403205");
        pizzaOrder2.setOrderTime(orderTime);

        orderDetail2.setPizzaOrder(pizzaOrder2);
        pizzaOrder2.addOrderDetail(orderDetail2);

        PizzaOrder pizzaOrderAfterSave2 = pizzaOrderService.recordPizzaOrder(pizzaOrder2);

        Assert.assertNotNull(pizzaOrderAfterSave2);

        Assert.assertEquals(pizzaOrderAfterSave2.getId(), pizzaOrderAfterSave.getId());
        Assert.assertEquals(pizzaOrderAfterSave2.getOrderReferenceNumber(), "784690331588403205");

        pizzaOrderRepository.flush();

        Assert.assertEquals(pizzaOrderRepository.count(), recordCount);
    }

}
