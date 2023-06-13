package com.rest_api.fs14backend.orderItem;

import com.rest_api.fs14backend.order.Order;
import com.rest_api.fs14backend.product.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem toOrderItem(OrderItemDTO orderItem, Product product, Order order){
        return new OrderItem(orderItem.getQuantity(), product, order);
    }

}
