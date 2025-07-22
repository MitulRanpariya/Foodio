package com.example.foodorderback.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodorderback.dto.FinalOrderDTO;
import com.example.foodorderback.dto.FinalOrderIdAndStatusDTO;
import com.example.foodorderback.dto.ItemFromCartDTO;
import com.example.foodorderback.dto.OrderItemDTO;
import com.example.foodorderback.model.FinalOrder;
import com.example.foodorderback.model.OrderItem;
import com.example.foodorderback.model.User;
import com.example.foodorderback.repository.FinalOrderRepository;
import com.example.foodorderback.repository.OrderItemRepository;
import com.example.foodorderback.service.FinalOrderService;
import com.example.foodorderback.service.MealService;
import com.example.foodorderback.service.UserService;

@Service
public class FinalOrderServiceImpl implements FinalOrderService {

    @Autowired private FinalOrderRepository finalOrderRepo;
    @Autowired private OrderItemRepository orderItemRepo;
    @Autowired private UserService userService;
    @Autowired private MealService mealService;

    @Override
    public List<FinalOrderDTO> getAllActiveFinalOrders() {
        return finalOrderRepo.findAll().stream()
            .filter(fo -> "ORDERED".equals(fo.getStatus()) || "IN PREPARATION".equals(fo.getStatus()))
            .map(FinalOrderDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<FinalOrderDTO> getAllMyActiveFinalOrders(Long userId) {
        return finalOrderRepo.findAll().stream()
            .filter(fo -> fo.getUser() != null && fo.getUser().getId() != null)
            .filter(fo -> fo.getUser().getId().equals(userId))
            .filter(fo -> "ORDERED".equals(fo.getStatus()) || "IN PREPARATION".equals(fo.getStatus()))
            .map(FinalOrderDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<FinalOrderDTO> getAllMyDeliveredFinalOrders(Long userId) {
        return finalOrderRepo.findAll().stream()
            .filter(fo -> "IN DELIVERY".equals(fo.getStatus()) &&
                          fo.getUser() != null &&
                          userId.equals(fo.getUser().getId()))
            .map(FinalOrderDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<FinalOrderDTO> getAllDeliveredFinalOrders() {
        return finalOrderRepo.findAll().stream()
            .filter(fo -> "IN DELIVERY".equals(fo.getStatus()))
            .map(FinalOrderDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public FinalOrder findOne(Long id) {
        return finalOrderRepo.findById(id).orElse(null);
    }

    @Override
    public FinalOrder save(FinalOrder finalOrder) {
        return finalOrderRepo.save(finalOrder);
    }

    @Override
    public Long makeFinalOrder(OrderItemDTO dto) {
        try {
            FinalOrder order = new FinalOrder();
            order.setDate(new Date());
            order.setStatus("ORDERED");
            order.setFinalPrice(dto.getFinalPrice());

            User user = userService.getCurrentUser();
            if (user != null) {
                order.setUser(user);
                order.setAddress(user.getAddress());
                order.setPhoneNumber(user.getPhoneNumber());
            } else {
                order.setAddress(dto.getAddress());
                order.setPhoneNumber(dto.getPhoneNumber());
            }

            FinalOrder savedOrder = finalOrderRepo.save(order);

            for (ItemFromCartDTO item : dto.getItemsFromCart()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setMeal(mealService.findOne(item.getMealId()));
                orderItem.setMealDescription(item.getMealDescription());
                orderItem.setMealImageName(item.getMealImageName());
                orderItem.setMealName(item.getMealName());
                orderItem.setMealPrice(item.getMealPrice());
                orderItem.setMealTypeName(item.getMealTypeName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setFinalOrder(savedOrder);
                orderItemRepo.save(orderItem);
            }

            return savedOrder.getId();
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public String setFinalOrderToDelivered(Long orderId) {
        return updateOrderStatus(orderId, "DELIVERED");
    }

    @Override
    public String changeFinalOrderStatus(FinalOrderIdAndStatusDTO dto) {
        return updateOrderStatus(dto.getActiveOrderId(), dto.getStatus());
    }

    private String updateOrderStatus(Long orderId, String status) {
        try {
            FinalOrder order = findOne(orderId);
            if (order != null) {
                order.setStatus(status);
                finalOrderRepo.save(order);
                return "success";
            }
        } catch (Exception ignored) {}
        return "fail";
    }

    @Override
    public String delete(Long finalOrderId) {
        try {
            FinalOrder order = findOne(finalOrderId);
            if (order != null) {
                List<OrderItem> items = orderItemRepo.findAll().stream()
                    .filter(item -> item.getFinalOrder().getId().equals(finalOrderId))
                    .collect(Collectors.toList());

                orderItemRepo.deleteAll(items);
                finalOrderRepo.delete(order);
                return "success";
            }
        } catch (Exception ignored) {}
        return "fail";
    }
}
