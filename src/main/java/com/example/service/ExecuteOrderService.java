package com.example.service;

import com.example.domain.Order;
import com.example.domain.OrderStatus;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 注文処理を行うコントローラ.
 */
@Service
public class ExecuteOrderService {
    @Autowired
    private OrderRepository repository;

    /**
     * 注文処理を行う.
     *
     * @param form 注文情報の入力フォーム
     */
    public void executeOrder(OrderForm form){
        Order order = repository.findById(form.getOrderId());
        order.setId(form.getOrderId());
        order.setDestinationName(form.getName());
        order.setDestinationEmail(form.getEmail());
        order.setDestinationZipcode(form.getZipcode());
        order.setDestinationPrefecture(form.getPrefecture());
        order.setDestinationMunicipalities(form.getMunicipalities());
        order.setDestinationAddress(form.getAddress());
        order.setDestinationTel(form.getTelephone());


        //TODO:
        order.setStatus(OrderStatus.UNPAID.getCode());

        // Timestamp から java.util.Date に変換
        if (form.getDeliveryTime() != null) {
            order.setDeliveryTime(new Date(form.getDeliveryTime().getTime()));
        }

        order.setPaymentMethod(form.getPaymentMethod());
        repository.update(order);
    }
}
