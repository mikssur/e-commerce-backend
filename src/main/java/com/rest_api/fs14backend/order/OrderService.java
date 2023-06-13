package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOne(OrderDTO orderDTO) {
        Long userId = orderDTO.getUserId();
        User user = userService.findById(userId);
        Order order = orderMapper.toOrder(orderDTO, user);

        return orderRepository.save(order);
    }

    public void deleteOne(Long id) {
        orderRepository.deleteById(id);
    }
}
