package com.challenge.order.receiver.bean.incoming.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

public class CreateOrderResponse {

    @Schema(description = "Unique reference number of the order (provided by caller)", example = "784690331588403203")
    @JsonProperty("orderReferenceNo")
    private String orderReferenceNo;

    @Schema(description = "Time when the order is placed (provided by caller, in ISO-8601 format)", example = "2021-12-05T16:00:00.000+08:00")
    @JsonProperty("orderTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime orderTime;

    @Schema(description = "Transaction number of the order", example = "784690292656873473")
    @JsonProperty("orderTransNo")
    private String orderTransNo;

    @Schema(description = "Total price of the order", example = "300.00")
    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    // Getters & Setters

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

    public String getOrderTransNo() {
        return orderTransNo;
    }

    public void setOrderTransNo(String orderTransNo) {
        this.orderTransNo = orderTransNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        // Force to 2 d.p.
        this.totalPrice = (totalPrice == null? null: totalPrice.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Override
    public String toString() {
        return "CreateOrderResponse{" +
                "orderReferenceNo='" + orderReferenceNo + '\'' +
                ", orderTime=" + orderTime +
                ", orderTransNo='" + orderTransNo + '\'' +
                '}';
    }
}
