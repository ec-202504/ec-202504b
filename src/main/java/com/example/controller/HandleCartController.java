package com.example.controller;

import com.example.form.OrderItemForm;
import com.example.service.HandleCartService;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleCartController {

    @Autowired
    private HandleCartService handleCartService;

    public String add(OrderItemForm orderItemForm) {
        // Logic to add item to cart
        return "cart";
    }

    public String delete(Integer orderId) {
        // Logic to delete item from cart
        return "cart";
    }

    public String showCart() {
        // Logic to show cart
        return "itemDetail";
    }
}
