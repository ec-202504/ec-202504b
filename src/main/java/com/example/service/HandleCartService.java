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


    /**
     * カートに商品を追加します.
     *
     * @param orderItemForm 追加する商品の情報を持つフォーム
     */
    public void add(OrderItemForm orderItemForm) {
        // 1. ログインユーザーID取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        // TODO: usernameからuserIdを取得する実装に修正
        Integer userId = 1;

        // カート（注文前Order）の有無を確認
        Order order = orderRepository.findByUserIdAndStatus(userId, 0);

        // カートがなければ新規作成
        if (order == null) {
            order = new Order();
            order.setUserId(userId);
            order.setStatus(0);
            // 必要な初期値をセット
            orderRepository.insert(order);
        }

        // OrderItem生成しフォーム値をセット
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setItemId(orderItemForm.getItemId());
        orderItem.setQuantity(orderItemForm.getQuantity());
        orderItem.setOptionItemSize(orderItemForm.getOptionItmSize());
        orderItem.setOptionShoesSize(orderItemForm.getOptionShoesSize());
        // OrderItemをDBに保存
        orderItemRepository.insert(orderItem);
    }

    /**
     * カートから商品を削除します.
     *
     * @param orderItemId 削除する商品のID
     */
    public void delete(Integer orderItemId) {
        // 指定IDの商品をカートから削除
        orderItemRepository.deleteByOrderItemId(orderItemId);
    }

    /**
     * 現在ログイン中のユーザーのカートを取得.
     *
     * @param userId　ユーザーID
     * @return カートの中身
     */
    public Order showCart(Integer userId){
        // カート状態（stat=0 購入前）のorderを取得
        return orderRepository.findByUserIdAndStatus(userId, 0); // This should return an Order object representing the user's cart
    }
}
