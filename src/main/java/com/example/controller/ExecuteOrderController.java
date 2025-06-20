package com.example.controller;

import com.example.form.OrderForm;
import com.example.service.ExecuteOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
     * @param form 入力フォーム
     * @return 注文完了ページに遷移する
     */
     @PostMapping("/executeOrder")
    public String executeOrder(OrderForm form){
        return "orderFinished";
    }
}
