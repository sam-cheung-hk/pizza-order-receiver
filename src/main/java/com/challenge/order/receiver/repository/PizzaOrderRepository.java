package com.challenge.order.receiver.repository;

import com.challenge.order.receiver.bean.db.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, String> {

    /**
     * Find {@link PizzaOrder} by order reference number
     *
     * @param orderReferenceNumber order reference number
     * @return target {@link PizzaOrder}, NULL if no match was found
     */
    PizzaOrder findByOrderReferenceNumber(String orderReferenceNumber);

}
