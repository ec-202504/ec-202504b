package com.example.service;

import com.example.domain.Order;
import com.example.domain.OrderStatus;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 注文処理を行うコントローラ.
 */
@Service
public class ExecuteOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 注文処理を行う.
     *
     * @param orderForm 注文情報の入力フォーム
     */
    public void executeOrder(OrderForm orderForm){
        Order order = orderRepository.findById(orderForm.getOrderId());
        order.setId(orderForm.getOrderId());
        order.setDistationName(orderForm.getName());
        order.setDistationEmail(orderForm.getEmail());
        order.setDistationZipcode(orderForm.getZipcode());
        order.setDistationPrefecture(orderForm.getPrefecture());
        order.setDistationMunicipalities(orderForm.getMunicipalities());
        order.setDistationAddress(orderForm.getAddress());
        order.setDistationTel(orderForm.getTelephone());

        //TODO:
        order.setStatus(OrderStatus.UNPAID.getCode());

//        // Timestamp から java.util.Date に変換
//        if (orderForm.getDeliveryTime() != null) {
//            order.setDeliveryTime(new Date(orderForm.getDeliveryTime().getTime()));
//        }

        order.setPaymentMethod(orderForm.getPaymentMethod());
        orderRepository.update(order);
    }

    /**
     * ユーザーIDからユーザーを取得する.
     *
     * @param userId
     * @return ユーザー，存在しない場合はnull
     */
    public User findByUserId(Integer userId){
        User user = userRepository.findById(userId);
        return user;
    }


    /**
     * Userの情報をOrderFormにコピーする
     *
     * @param user
     * @param orderForm
     */
    public void copyUserInfoToOrderForm(User user, OrderForm orderForm){
        orderForm.setName(user.getName());
        orderForm.setEmail(user.getEmail());
        orderForm.setZipcode(user.getZipcode());
        orderForm.setPrefecture(user.getPrefecture());
        orderForm.setMunicipalities(user.getMunicipalities());
        orderForm.setAddress(user.getAddress());
        orderForm.setTelephone(user.getTelephone());
    }
}
