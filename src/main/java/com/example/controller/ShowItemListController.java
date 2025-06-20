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
 * 商品リストを表示する機能の制御を行うコントローラクラス.
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

        // あいまい検索で一件もヒットしなかったらエラーメッセージとともに全件表示する。
        if (itemList.isEmpty()) {
            model.addAttribute("nonExistError", "該当する商品がありません。");
            model.addAttribute("itemList", showItemListService.searchAll());
            return "item_list_bousai";
        }

        model.addAttribute("itemList", itemList);

        return "item_list_bousai";
    }
}
