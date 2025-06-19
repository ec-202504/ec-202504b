package com.example.controller;

import com.example.form.OrderItemForm;
import com.example.service.HandleCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * カートの操作を管理するコントローラーです。
 * 商品の追加、削除、カートの表示を行います。
 */
@Controller
@RequestMapping("/cart")
public class HandleCartController {

    @Autowired
    private HandleCartService handleCartService;

    /**
     * カートに商品を追加します。
     *
     * @param orderItemForm 追加する商品の情報を持つフォーム
     * @return カート表示画面へのリダイレクト
     */
    public String add(OrderItemForm orderItemForm) {
        Integer userId = 1; // 仮のユーザーID。実際は認証情報から取得する必要があります。
        handleCartService.add(orderItemForm);
        return "redirect:/cart/show";
    }

    public String delete(Integer orderId) {
        // Logic to delete item from cart
        return "cart";
    }

    /**
     * カートの内容を表示します。
     *
     * @return カートの詳細を表示するビュー名
     */
    @RequestMapping("/show")
    public String showCart() {
        return "cart";
    }
}
