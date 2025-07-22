package com.example.foodorderback.service;

import java.util.List;

import com.example.foodorderback.dto.FinalOrderDTO;
import com.example.foodorderback.dto.FinalOrderIdAndStatusDTO;
import com.example.foodorderback.dto.OrderItemDTO;
import com.example.foodorderback.model.FinalOrder;

public interface FinalOrderService {

    FinalOrder save(FinalOrder finalOrder);
    Long makeFinalOrder(OrderItemDTO orderItemDTO);
    FinalOrder findOne(Long id);
    List<FinalOrderDTO> getAllActiveFinalOrders();
    List<FinalOrderDTO> getAllDeliveredFinalOrders();
    List<FinalOrderDTO> getAllMyActiveFinalOrders(Long userId);
    List<FinalOrderDTO> getAllMyDeliveredFinalOrders(Long userId);
    String setFinalOrderToDelivered(Long orderId);
    String changeFinalOrderStatus(FinalOrderIdAndStatusDTO dto);
    String delete(Long orderId);
}
