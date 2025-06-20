package com.example.controller;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.ExecuteOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("") //TODO:後で変える
public class ExecuteOrderController {

    @Autowired
    private ExecuteOrderService service;

    /**
     * 注文処理を行い，注文を完了する.
     *
     * @param orderForm 入力フォーム
     * @return 注文完了ページに遷移する
     */
     @PostMapping("/executeOrder")
    public String executeOrder(OrderForm orderForm){
         service.executeOrder(orderForm);
        return "orderFinished";
    }

    /**
     * 「ログイン者情報を入力」ボタンが押された場合はログイン者の情報をフォームに入力する
     * @return フォームを埋めたページ
     */
    @PostMapping("/fillOutFormByUserInfo")
    public String fillOutFormByUserInfo(OrderForm orderForm, Model model){
        int userId = 1; //TODO:後で治す
        final  int orderId = 1;
        User user = service.findByUserId(userId);
        service.copyUserInfoToOrderForm(user, orderForm);
        Order order = service.showCart(orderId);;
        model.addAttribute("order", order);
        System.out.println(orderForm);
        return "confirmOrder";
    }
}
