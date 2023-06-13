package com.rest_api.fs14backend.orderItem;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.order.Order;
import com.rest_api.fs14backend.order.OrderService;
import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://e-commerce-frontend-livid.vercel.app/")
@RequestMapping("api/v1/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderItem> getOrderItem() {return orderItemService.getAll();}

    @PostMapping
    public OrderItem createOne(@RequestBody OrderItemDTO orderItemDTO) {
        return orderItemService.createOrderItem(orderItemDTO);
    }

    @GetMapping("/{id}")
    public OrderItem findById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.findById(id);

        return orderItem;
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        OrderItem orderItem  = orderItemService.findById(id);

        orderItemService.deleteById(id);
    }
}
