package com.example.service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConfirmOrderService {

    @Autowired
    OrderRepository repository;

    /**
     * 注文IDから注文を返す.
     *
     * @param orderId 注文ID
     * @return 注文商品一覧を含めた注文を返す
     */
    public Order showCart(Integer orderId) {
        Order order = repository.findById(orderId);
        return order;
    }
}
