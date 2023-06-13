package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.user.User;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toOrder(OrderDTO order, User user){
        return new Order(order.getPurchaseAt(),user);
    }
}
