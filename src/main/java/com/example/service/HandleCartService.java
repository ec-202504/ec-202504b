package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Order;
import com.example.form.OrderItemForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;

public class HandleCartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    public void addItem(OrderItemForm orderItemForm) {

    }

    public void delete(Integer orderId) {

    }

    public Order showCart(){
        return null; // This should return an Order object representing the user's cart
    }
}
