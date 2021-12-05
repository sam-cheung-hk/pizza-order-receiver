package com.challenge.order.receiver.service;

import com.challenge.order.receiver.bean.db.PizzaOrder;
import com.challenge.order.receiver.bean.db.PizzaOrderDetail;
import com.challenge.order.receiver.repository.PizzaOrderRepository;
import com.challenge.order.receiver.util.UniqueIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PizzaOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaOrderService.class);

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;

    /**
     * Calculate the total price of pizza order and record the order
     *
     * @param pizzaOrder {@link PizzaOrder} to be recorded
     * @return recorded pizza order
     */
    @Transactional(rollbackOn = Exception.class)
    public PizzaOrder recordPizzaOrder(PizzaOrder pizzaOrder) {
        // Check if the order is already processed before
        PizzaOrder existingOrder = pizzaOrderRepository.findByOrderReferenceNumber(pizzaOrder.getOrderReferenceNumber());
        if (existingOrder == null) {
            // Record as new order

            // Assign unique ID & Update the total price
            pizzaOrder.setId(uniqueIdGenerator.getNextIdInString());
            BigDecimal totalPrice = BigDecimal.ZERO;
            if (pizzaOrder.getOrderDetails() != null) {
                for (PizzaOrderDetail orderDetail : pizzaOrder.getOrderDetails()) {
                    orderDetail.setId(uniqueIdGenerator.getNextIdInString());
                    totalPrice = totalPrice.add(orderDetail.getPrice());
                }
            }
            LOGGER.debug("Pizza Order: {}, Total price of pizza order: {}", pizzaOrder, totalPrice);

            pizzaOrder.setTotalPrice(totalPrice);
            pizzaOrderRepository.saveAndFlush(pizzaOrder);
            LOGGER.info("Created pizza order. Pizza Order: {}", pizzaOrder);

            return pizzaOrder;
        } else {
            // Return existing order
            LOGGER.warn("Duplicated record order request. Pizza Order: {}", existingOrder);
            return existingOrder;
        }
    }

}
