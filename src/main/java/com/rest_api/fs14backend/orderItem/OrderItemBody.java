package com.rest_api.fs14backend.orderItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemBody {
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private int quantity;

    public OrderItemBody(Long productId, int quantity ){
        this.productId = productId;
        this.quantity = quantity;
    }
}
