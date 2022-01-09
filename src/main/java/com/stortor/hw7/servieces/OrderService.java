package com.stortor.hw7.servieces;

import com.stortor.hw7.dto.OrderItemDto;
import com.stortor.hw7.entity.Order;
import com.stortor.hw7.entity.Product;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.exceptions.ResourceNotFoundException;
import com.stortor.hw7.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        Order orderWithoutOrderItems = new Order(order.getUser(), order.getTotalPrice(), order.getAddress(), order.getPhone());
        orderRepository.save(orderWithoutOrderItems);
        return orderWithoutOrderItems;
    }

    public List<Order> showAllOrders() {
        return orderRepository.findAllOrders();
    }
}