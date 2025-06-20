package com.example.service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 注文履歴表示処理を行うサービスクラス.
 */
@Service
@Transactional
public class ShowOrderHistoryService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 注文履歴表示.
     *
     * @param userId ユーザーID
     * @return 注文履歴
     */
    public List<Order> showOrderHistory(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
}
