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
    public void executeOrder(int userId, OrderForm orderForm){
        Order order = orderRepository.findByUserIdAndStatus0(userId);
        order.setId(orderForm.getOrderId());
        order.setDestinationName(orderForm.getName());
        order.setDestinationEmail(orderForm.getEmail());
        order.setDestinationZipcode(orderForm.getZipcode());
        order.setDestinationPrefecture(orderForm.getPrefecture());
        order.setDestinationMunicipalities(orderForm.getMunicipalities());
        order.setDestinationAddress(orderForm.getAddress());
        order.setDestinationTel(orderForm.getTelephone());


        //TODO:
        order.setStatus(OrderStatus.UNPAID.getCode()); //入金待ちに変更
        order.setTotalPrice(order.getTotalPrice()); //税込み合計金額

        // Timestamp から java.util.Date に変換
//        if (orderForm.getDeliveryTime() != null) {
//            order.setDeliveryTime(new Date(orderForm.getDeliveryTime().getTime()));
//        }

        order.setPaymentMethod(orderForm.getPaymentMethod());

        orderRepository.update(order);
    }

    /**
     * ユーザーIDからユーザーを取得する.
     *
     * @param userId ユーザーID
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
