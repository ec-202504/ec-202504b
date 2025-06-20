package com.example.controller;

import com.example.service.HandleCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.OrderItemForm;

import jakarta.validation.Valid;

/**
 * カートの操作を管理するコントローラーです.
 * 商品の追加、削除、カートの表示を行います。
 */
@Controller
@RequestMapping("/cart")
public class HandleCartController {

    @Autowired
    private HandleCartService handleCartService;

    @Autowired
    private HttpSession session;

    /**
     * カートに商品を追加します.
     *
     * @param orderItemForm 追加する商品の情報を持つフォーム
     * @return カート表示画面へのリダイレクト
     */
    @PostMapping("/add")
    public String add(@Valid OrderItemForm orderItemForm, BindingResult result) {
        // セッションからユーザーIDを取得
        Integer userId = (Integer) session.getAttribute("userId");
        // セッションが切れた時にlogin画面へリダイレクト
        if (userId == null) {
            return "redirect:/login";
        }
        // 入力チェックエラーがある場合、商品詳細画面に戻る
        if(result.hasErrors()){
            return "itemDetail";
        }
        // 商品をカートに追加
        handleCartService.add(orderItemForm, userId);
        // カート画面へリダイレクト
        return "redirect:/cart/show";
    }

    /**
     * カートから商品を削除します.
     * カートを表示する際に、削除したい商品のIDを指定します。
     * カート全体ではなく、特定の商品を削除するためのメソッドです。
     *
     * @param orderItemId 削除する商品のID
     * @return カート表示画面へのリダイレクト
     */
    @RequestMapping("/delete")
    public String delete(Integer orderItemId) {
        // セッションからユーザーIDを取得
        Integer userId = (Integer) session.getAttribute("userId");
        //セッションが切れた時にlogin画面へリダイレクト
        if (userId == null) {
            return "redirect:/login";
        }
        //商品をカートから削除
        handleCartService.delete(orderItemId, userId);
        // カート画面へリダイレクト
        return "redirect:/cart/show";
    }

    /**
     * カートの内容を表示します.
     *
     * @return カートの詳細を表示するビュー名
     */
    @RequestMapping("/show")
    public String showCart(Model model) {
        // セッションからユーザーIDを取得
        Integer userId = (Integer) session.getAttribute("userId");
        //セッションが切れた時にlogin画面へリダイレクト
        if (userId == null) {
            return "redirect:/login";
        }
        // カートの内容を取得
        Order order = handleCartService.showCart(userId);
        // カートが空の場合、orderはnullになる可能性があるため、nullチェックを行う
        if (order == null) {
            order = new Order(); // 空のOrderオブジェクトを作成
        }
        // カートの内容を追加
        model.addAttribute("order", order);
        //カート画面を表示
        return "cart";
    }
}
