package com.example.foodorderback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.foodorderback.dto.*;
import com.example.foodorderback.model.FinalOrder;
import com.example.foodorderback.model.User;
import com.example.foodorderback.service.FinalOrderService;
import com.example.foodorderback.service.OrderItemService;
import com.example.foodorderback.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("api/finalOrder")
public class FinalOrderController {

    @Autowired
    private FinalOrderService finalOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @PostMapping("/createFinalOrder")
    public ResponseEntity<Long> saveFinalOrderUser(@RequestBody OrderItemDTO orderItemDTO) {
        Long id = finalOrderService.makeFinalOrder(orderItemDTO);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/getFinalOrderById/{id}")
    public ResponseEntity<FinalOrder> getFinalOrderById(@PathVariable Long id) {
        FinalOrder finalOrder = finalOrderService.findOne(id);
        return ResponseEntity.ok(finalOrder != null ? finalOrder : new FinalOrder());
    }

    @GetMapping("/getAllDeliveredFinalOrders")
    public ResponseEntity<List<FinalOrderDTO>> getAllDeliveredFinalOrders() {
        return ResponseEntity.ok(finalOrderService.getAllDeliveredFinalOrders());
    }

    @GetMapping("/getAllActiveFinalOrders")
    public ResponseEntity<List<FinalOrderDTO>> getAllActiveFinalOrders() {
        return ResponseEntity.ok(finalOrderService.getAllActiveFinalOrders());
    }

    @GetMapping("/getAllMyActiveFinalOrders")
    public ResponseEntity<List<FinalOrderDTO>> getAllMyActiveFinalOrders() {
        try {
            User currentUser = userService.getCurrentUser();
            return ResponseEntity.ok(finalOrderService.getAllMyActiveFinalOrders(currentUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllMyDeliveredFinalOrders")
    public ResponseEntity<List<FinalOrderDTO>> getAllMyDeliveredFinalOrders() {
        try {
            User currentUser = userService.getCurrentUser();
            return ResponseEntity.ok(finalOrderService.getAllMyDeliveredFinalOrders(currentUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getOrderItemsByFinalOrderId/{id}")
    public ResponseEntity<List<ItemFromCartDTO>> getOrderItemsByFinalOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getItemFromCartByFinalOrderId(id));
    }

    @PutMapping("/setFinalOrderToDelivered/{finalOrderId}")
    public ResponseEntity<String> setFinalOrderToDelivered(@PathVariable Long finalOrderId) {
        return ResponseEntity.ok(finalOrderService.setFinalOrderToDelivered(finalOrderId));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<String> editStatus(@RequestBody FinalOrderIdAndStatusDTO dto) {
        return ResponseEntity.ok(finalOrderService.changeFinalOrderStatus(dto));
    }

    @DeleteMapping("/deleteFinalOrder/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(finalOrderService.delete(id));
    }
}
