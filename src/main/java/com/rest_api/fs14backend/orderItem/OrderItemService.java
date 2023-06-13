package com.rest_api.fs14backend.orderItem;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.order.Order;
import com.rest_api.fs14backend.order.OrderDTO;
import com.rest_api.fs14backend.order.OrderService;
import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired OrderItemRepo orderItemRepo;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired OrderItemMapper orderItemMapper;

    public List<OrderItem> getAll() { return orderItemRepo.findAll(); }

    public OrderItem createOrderItem(OrderItemDTO orderItemDTO) {
        Long productId = orderItemDTO.getProductId();
        Product product = productService.findById(productId);

        Long orderId = orderItemDTO.getOrderId();
        Order order = orderService.findById(orderId);

        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDTO, product, order);

        return orderItemRepo.save(orderItem);
    }

    public void createOrderItems(OrderDTO orderDTO, Order order) {
        List<OrderItemBody> orderItems = orderDTO.getOrderItemList();

        for (OrderItemBody item : orderItems) {
            Long productId = item.getProductId();
            Product product = productService.findById(productId);

            int quantity = item.getQuantity();

            OrderItem orderItem = new OrderItem(quantity, product, order);
            orderItemRepo.save(orderItem);
        }
    }

    public OrderItem findById(Long id){
        OrderItem orderItem = orderItemRepo.findById(id).orElse(null);

        if (orderItem == null) {
            throw new NotFoundException("OrderItem not found");
        }
        return orderItem;
    }

    public void deleteById(Long id) {
        OrderItem orderItem = orderItemRepo.findById(id).orElse(null);

        if (orderItem == null) {
            throw new NotFoundException("OrderItem not found");
        }
        orderItemRepo.deleteById(id);
    }
}
