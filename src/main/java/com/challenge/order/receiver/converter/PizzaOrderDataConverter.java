package com.challenge.order.receiver.converter;

import com.challenge.order.receiver.bean.db.PizzaOrder;
import com.challenge.order.receiver.bean.db.PizzaOrderDetail;
import com.challenge.order.receiver.bean.incoming.request.CreateOrderRequest;
import com.challenge.order.receiver.bean.incoming.response.CreateOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class PizzaOrderDataConverter {

    /**
     * Transform from {@link CreateOrderRequest} to {@link PizzaOrder}
     *
     * @param request {@link CreateOrderRequest} to be transformed
     * @return transformed {@link PizzaOrder}
     */
    public PizzaOrder transformToPizzaOrder(CreateOrderRequest request) {
        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setOrderReferenceNumber(request.getOrderReferenceNo());
        pizzaOrder.setOrderTime(request.getOrderTime());

        // Setup details
        if (request.getOrderDetails() != null) {
            for (CreateOrderRequest.OrderDetail requestOrderDetail : request.getOrderDetails()) {
                PizzaOrderDetail orderDetail = new PizzaOrderDetail();
                orderDetail.setPizzaOrder(pizzaOrder);
                orderDetail.setName(requestOrderDetail.getName());
                orderDetail.setQuantity(requestOrderDetail.getQuantity());
                orderDetail.setPrice(requestOrderDetail.getPrice());

                pizzaOrder.addOrderDetail(orderDetail);
            }
        }

        return pizzaOrder;
    }

    /**
     * Transform from {@link PizzaOrder} to {@link CreateOrderResponse}
     *
     * @param pizzaOrder {@link PizzaOrder} to be transformed
     * @return transformed {@link CreateOrderResponse}
     */
    public CreateOrderResponse transformToCreateOrderResponse(PizzaOrder pizzaOrder) {
        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderReferenceNo(pizzaOrder.getOrderReferenceNumber());
        response.setOrderTime(pizzaOrder.getOrderTime());
        response.setOrderTransNo(pizzaOrder.getId());
        response.setTotalPrice(pizzaOrder.getTotalPrice());

        return response;
    }

}
