package com.example.foodorderback.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodorderback.dto.ItemFromCartDTO;
import com.example.foodorderback.model.OrderItem;
import com.example.foodorderback.repository.OrderItemRepository;
import com.example.foodorderback.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsByFinalOrderId(Long finalOrderId) {
        return orderItemRepository.findAll().stream()
                .filter(oi -> oi.getFinalOrder() != null && finalOrderId.equals(oi.getFinalOrder().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemFromCartDTO> getItemFromCartByFinalOrderId(Long finalOrderId) {
        return orderItemRepository.findAll().stream()
                .filter(oi -> oi.getFinalOrder() != null && finalOrderId.equals(oi.getFinalOrder().getId()))
                .map(ItemFromCartDTO::new)
                .collect(Collectors.toList());
    }
}
