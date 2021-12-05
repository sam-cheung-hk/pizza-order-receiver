package com.challenge.order.receiver.controller;

import com.challenge.order.receiver.bean.db.PizzaOrder;
import com.challenge.order.receiver.bean.incoming.request.CreateOrderRequest;
import com.challenge.order.receiver.bean.incoming.response.CreateOrderResponse;
import com.challenge.order.receiver.converter.PizzaOrderDataConverter;
import com.challenge.order.receiver.service.PizzaOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private PizzaOrderDataConverter pizzaOrderDataConverter;

    @Autowired
    private PizzaOrderService pizzaOrderService;

    @Operation(summary = "Create pizza order",
            description = "Record pizza order into database",
            tags = { "Pizza Order" }
    )
    @ApiResponse(responseCode = "200",
            description = "Successful Operation",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateOrderResponse.class)))
    @PostMapping("/create")
    public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        LOGGER.debug("Create pizza order request. Request: {}", request);

        // Transform to Pizza Order
        PizzaOrder order = pizzaOrderDataConverter.transformToPizzaOrder(request);

        // Record the order
        order = pizzaOrderService.recordPizzaOrder(order);

        return pizzaOrderDataConverter.transformToCreateOrderResponse(order);
    }

}
