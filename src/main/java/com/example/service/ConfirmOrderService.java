package com.example.service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注文確認処理を行うためのサービス
 */
@Service
public class ConfirmOrderService {

    @Autowired
    OrderRepository repository;

    /**
     * ユーザーIDから注文を返す.
     *
     * @param userId ユーザーID
     * @return 注文商品一覧を含めた注文を返す
     */
    public Order getOrder(Integer userId) {
        Order order = repository.findByUserIdAndStatus0(userId);
        return order;
    }
}
