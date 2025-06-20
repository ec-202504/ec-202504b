package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.ConfirmOrderService;
import com.example.service.ExecuteOrderService;
import com.example.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文確認から実際に注文するまでを行うコントローラ
 */
@Controller
@RequestMapping("")
public class ExecuteOrderController {

    @Autowired
    private ExecuteOrderService executeOrderService;

    @Autowired
    private ConfirmOrderService confirmOrderService;

    @Autowired
    private MailService mailService;

    /**
     * 「ログイン者情報を入力」ボタンが押された場合はログイン者の情報をフォームに入力する
     */
    @GetMapping("/fillOutFormByUserInfo")
    public String fillOutFormByUserInfo(Model model, @AuthenticationPrincipal LoginUser loginUser) {
        final int userId = loginUser.getUser().getId();

        //ユーザー情報をユーザーフォームに格納する
        OrderForm orderForm = new OrderForm();
        User user = executeOrderService.findByUserId(userId);
        executeOrderService.copyUserInfoToOrderForm(user, orderForm);

        //注文確認画面に戻るために注文を再度取得する
        Order order = confirmOrderService.getOrder(userId);
        model.addAttribute("order", order);
        return toConfirmOrder(orderForm, model, loginUser);

    }

    /**
     * 注文確認画面を遷移する.
     *
     * @param orderForm 入力フォーム
     * @return 注文確認に遷移する.
     */
    @PostMapping("/toConfirmOrder")
    String toConfirmOrder(OrderForm orderForm, Model model, @AuthenticationPrincipal LoginUser loginUser) {
        final int userId = loginUser.getUser().getId();
        Order order = confirmOrderService.getOrder(userId);

        model.addAttribute("order", order);
        model.addAttribute("orderForm", orderForm);
        model.addAttribute("tax", order.getTax());
        model.addAttribute("totalPrice", order.getTotalPrice());
        return "confirmOrder";
    }


    /**
     * 注文処理を行い，注文を完了する.
     *
     * @param orderForm 入力フォーム
     * @return 注文完了ページに遷移する
     */
    @PostMapping("/executeOrder")
    public String executeOrder(@Validated OrderForm orderForm, BindingResult result, Model model, @AuthenticationPrincipal LoginUser loginUser) {
        final int userId = loginUser.getUser().getId();
        orderForm.setOrderId(userId);

        //入力エラーがある場合
        if (result.hasErrors()) {
//            System.out.println("入力エラーがあります--------------------");
//            System.out.println(result);
            Order order = confirmOrderService.getOrder(userId);
            model.addAttribute("order", order);
            model.addAttribute("orderForm", orderForm);
            return "confirmOrder";
        }

        //注文処理を行う
        executeOrderService.executeOrder(userId, orderForm);
//        mailService.sendTestMail(); //TODO:メールの内容を後で決める

        return "orderFinished";
    }
}
