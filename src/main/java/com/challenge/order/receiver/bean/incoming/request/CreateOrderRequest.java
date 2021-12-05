package com.challenge.order.receiver.bean.incoming.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class CreateOrderRequest {

    @Schema(description = "Unique reference number of the order", required = true, example = "784690331588403203")
    @NotBlank(message = "orderReferenceNo is mandatory")
    @JsonProperty("orderReferenceNo")
    private String orderReferenceNo;

    @Schema(description = "Time when the order is placed (in ISO-8601 format)", required = true, example = "2021-12-05T16:00:00.000+08:00")
    @NotNull(message = "orderTime is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("orderTime")
    private ZonedDateTime orderTime;

    @Schema(description = "Detail of the order", required = true)
    @NotNull(message = "orderDetails is mandatory")
    @NotEmpty(message = "orderDetails cannot be empty")
    @Valid
    @JsonProperty("orderDetails")
    private List<OrderDetail> orderDetails;

    // Getters & setters

    public String getOrderReferenceNo() {
        return orderReferenceNo;
    }

    public void setOrderReferenceNo(String orderReferenceNo) {
        this.orderReferenceNo = orderReferenceNo;
    }

    public ZonedDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(ZonedDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "orderReferenceNo='" + orderReferenceNo + '\'' +
                ", orderTime=" + orderTime +
                ", orderDetails=" + (orderDetails == null? null: Arrays.toString(orderDetails.toArray())) +
                '}';
    }

    public static class OrderDetail {

        @Schema(description = "Pizza name", required = true, example = "PIZZA_TYPE_A")
        @NotBlank(message = "name in orderDetails is mandatory")
        @JsonProperty("name")
        private String name;

        @Schema(description = "Quantity of the pizza", required = true, example = "1")
        @NotNull(message = "quantity in orderDetails is mandatory")
        @Min(1)
        @JsonProperty("quantity")
        private Integer quantity;

        @Schema(description = "Sub-total price of the pizza", required = true, example = "99")
        @NotNull(message = "price in orderDetails is mandatory")
        @JsonProperty("price")
        private BigDecimal price;

        // Getters & setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "OrderDetail{" +
                    "name='" + name + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

}
