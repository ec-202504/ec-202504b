package com.example.controller;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.service.ConfirmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * 注文確認処理を行うコントローラ.
 */
@Controller
@RequestMapping("") //TODO:後で変える
public class ConfirmOrderController {

    @Autowired
    private ConfirmOrderService service;


    /**
     * 注文確認画面を遷移する.
     *
     * @param orderId 注文ID
     * @return 注文確認に遷移する.
     */
    @PostMapping("/toConfirmOrder")
    String toConfirmOrder(Integer orderId, Model model) {
//        final orderId = 1; //デバッグ用
        Order order = service.showCart(orderId);
        model.addAttribute("order", order);
        return "confirmOrder";
    }

    /**
     * TODO:テスト用のクラス，後で消す
     *
     * @param model
     * @return
     */
    @GetMapping("/test")
    String toTest(Model model){
        final  int orderId = 1;
        Order order = service.showCart(orderId);
        List<OrderItem> orderItemList = order.getOrderItemList();
        model.addAttribute("order", order);
        return "confirmOrder";
    }



}
