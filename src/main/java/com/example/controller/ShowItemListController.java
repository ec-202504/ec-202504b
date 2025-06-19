package com.example.controller;

import com.example.domain.Item;
import com.example.service.ShowItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品関連機能の制御を行うコントローラクラス.
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {

    @Autowired
    private ShowItemListService showItemListService;

    @GetMapping("/showItemList")
    public String searchAll(Model model) {
        List<Item> itemList = showItemListService.searchAll();
        model.addAttribute("itemList", itemList);

        return "item_list_bousai";
    }

    @PostMapping("/searchByName")
    public String searchByName(String name, Model model) {
        List<Item> itemList = showItemListService.findByName(name);
        model.addAttribute("itemList", itemList);

        return "item_list_bousai";
    }

    @GetMapping("/showItemDetail")
    public String showItemDetail(Integer itemId, Model model) {
        Item item = showItemListService.showItemDetail(itemId);
        model.addAttribute("item", item);

        return "item_detail";
    }
}
