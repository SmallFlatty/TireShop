package com.tirestore.tireshop.controller.admin;


import com.tirestore.tireshop.dto.response.OrderResponse;
import com.tirestore.tireshop.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/order")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/change-status/{id}")
    public OrderResponse changeOrderStatus(@PathVariable Integer id) {
        return orderService.changeOrderStatus(id);
    }
}
