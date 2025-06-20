package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.service.ShowOrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ShowOrderHistoryController {

    @Autowired
    private ShowOrderHistoryService showOrderHistoryService;

    @GetMapping("/showOrderHistory")
    public String showOrderHistory(Model model,
                                   @AuthenticationPrincipal LoginUser loginUser) {
        List<Order> orderList
                = showOrderHistoryService.showOrderHistory(loginUser.getUser().getId());
        model.addAttribute("orderList", orderList);

        return "orderHistory";
    }


}
