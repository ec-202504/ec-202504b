package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.OrderItemForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;

public class HandleCartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    public void add(OrderItemForm orderItemForm) {
        // 1. ログインユーザーID取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        // ここではusernameからユーザーIDを取得する想定（実際はUserRepository等でID取得が必要）
        // 仮にuserId=1とする（本来はDBから取得）
        Integer userId = 1;

        // 2. カート（注文前Order）の有無確認
        Order order = orderRepository.findByUserIdAndStatus(userId, 0);

        // 3. なければ新規作成
        if (order == null) {
            order = new Order();
            order.setUserId(userId);
            order.setStatus(0);
            // 必要な初期値をセット
            orderRepository.insert(order);
        }

        // 4. FormからOrderItem生成
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setItemId(orderItemForm.getItemId());
        orderItem.setQuantity(orderItemForm.getQuantity());
        orderItem.setOption1(orderItemForm.getOptionItmSize());
        orderItem.setOption2(orderItemForm.getOptionShoesSize());
        // 必要に応じて他の項目もセット

        // 5. OrderItemをDBに保存
        orderItemRepository.insert(orderItem);
    }

    public void delete(Integer orderId) {

    }

    public Order showCart(){
        return null; // This should return an Order object representing the user's cart
    }
}
