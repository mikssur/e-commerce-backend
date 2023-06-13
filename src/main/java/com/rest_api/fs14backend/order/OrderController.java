package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.orderItem.OrderItem;
import com.rest_api.fs14backend.orderItem.OrderItemService;
import com.rest_api.fs14backend.orderItem.OrderItemBody;
import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://e-commerce-frontend-livid.vercel.app/")
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        try {
            return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        try {
            Order order = orderService.findById(id);
            if (order == null) {
                throw new NotFoundException("Order not found");
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createOne(@RequestBody OrderDTO orderDTO) {
        try {
            Order createdOrder = orderService.createOne(orderDTO);

            orderItemService.createOrderItems(orderDTO, createdOrder);

            Long orderId = createdOrder.getId();

            Order order = orderService.findById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable Long id) {
        try {
            Order order = orderService.findById(id);
            if (order == null) {
                throw new NotFoundException("Order not found");
            }
            orderService.deleteOne(id);
            return new ResponseEntity<>("Order was deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>("Internal server error occurred, try to refresh the page", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
