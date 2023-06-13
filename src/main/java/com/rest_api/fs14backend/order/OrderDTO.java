package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.orderItem.OrderItemBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long userId;
    private Timestamp purchaseAt;
    private List<OrderItemBody> orderItemList;
}
