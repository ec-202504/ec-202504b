package com.example.controller;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.ConfirmOrderService;
import com.example.service.ExecuteOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文確認から実際に注文するまでを行うコントローラ
 */
@Controller
@RequestMapping("") //TODO:後で変える
public class ExecuteOrderController {

    @Autowired
    private ExecuteOrderService executeOrderService;

    @Autowired
    private ConfirmOrderService confirmOrderService;

    /**
     * 注文処理を行い，注文を完了する.
     *
     * @param orderForm 入力フォーム
     * @return 注文完了ページに遷移する
     */
    @PostMapping("/executeOrder")
    public String executeOrder(OrderForm orderForm) {
        System.out.println("executeOrder");
        executeOrderService.executeOrder(orderForm);
        return "orderFinished";
    }

    /**
     * 「ログイン者情報を入力」ボタンが押された場合はログイン者の情報をフォームに入力する
     */
    @GetMapping("/fillOutFormByUserInfo")
    public String fillOutFormByUserInfo(Model model) {
        //TODO:後で治す
        int userId = 1;
        final int orderId = 1;
        OrderForm orderForm = new OrderForm();
        User user = executeOrderService.findByUserId(userId);
        executeOrderService.copyUserInfoToOrderForm(user, orderForm);
        Order order = confirmOrderService.showCart(orderId);
        model.addAttribute("order", order);
        System.out.println(orderForm);
        return toConfirmOrder(orderId, orderForm, model);
    }

    /**
     * 注文確認画面を遷移する.
     *
     * @param orderId 注文ID
     * @return 注文確認に遷移する.
     */
    @PostMapping("/toConfirmOrder")
    String toConfirmOrder(Integer orderId, OrderForm orderForm, Model model) {
//        final orderId = 1; //デバッグ用
        Order order = confirmOrderService.showCart(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderForm", orderForm); // ← ★これが必要です！
        return "confirmOrder";
    }

    /**
     * TODO:テスト用のクラス，後で消す
     *
     * @param model
     * @return
     */
    @GetMapping("/test")
    String toTest(Model model) {
        final int orderId = 1;
        Order order = confirmOrderService.showCart(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderForm", new OrderForm());
        return "confirmOrder";
    }
}
