package com.example.controller;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;
import com.example.service.ShowItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品詳細を表示する機能の制御を行うコントローラクラス.
 */
@Controller
@RequestMapping("/")
public class ShowItemDetailController {

    @Autowired
    private ShowItemDetailService showItemDetailService;

    @GetMapping("/showItemDetail")
    public String showItemDetail(Integer itemId, Model model) {
        Item item = showItemDetailService.showItemDetail(itemId);
        model.addAttribute("item", item);

        return "item_detail";
    }
}
