package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.OrderItemForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ItemRepository;

@Service
public class HandleCartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;


    /**
     * カートに商品を追加します.
     *
     * @param orderItemForm 追加する商品の情報を持つフォーム
     */
    public void add(OrderItemForm orderItemForm, Integer userId) {
        System.out.println("[LOG] add() called. userId=" + userId);
        System.out.println("[LOG] orderItemForm: " + orderItemForm);
        // 1. ログインユーザーID取得
        Order order = orderRepository.findByUserIdAndStatus(userId, 0);
        System.out.println("[LOG] findByUserIdAndStatus result: " + order);
        // カートがなければ新規作成
        if (order == null) {
            order = new Order();
            order.setUserId(userId);
            order.setStatus(0);
            // 必要な初期値をセット
            orderRepository.insert(order);
            System.out.println("[LOG] new Order inserted. orderId=" + order.getId());
        } else {
            System.out.println("[LOG] existing Order. orderId=" + order.getId());
        }

        // OrderItem生成しフォーム値をセット
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setItemId(orderItemForm.getItemId());
        orderItem.setQuantity(orderItemForm.getQuantity());
        orderItem.setShoesSize(orderItemForm.getShoesSize());
        // 商品情報もセット
        orderItem.setItem(itemRepository.findById(orderItemForm.getItemId()));
        System.out.println("[LOG] OrderItem to insert: " + orderItem);
        // OrderItemをDBに保存
        orderItemRepository.insert(orderItem);
        System.out.println("[LOG] OrderItem inserted.");
    }

    /**
     * カートから商品を削除します.
     *
     * @param orderItemId 削除する商品のID
     */
    public void delete(Integer orderItemId, Integer userId) {
        // 1. ログインユーザーID取得
        Order order = orderRepository.findByUserIdAndStatus(userId, 0);
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
