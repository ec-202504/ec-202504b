package com.example.controller;

import com.example.domain.Item;
import com.example.form.QuestionnaireForm;
import com.example.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品おすすめ機能の制御を行うコントローラクラス.
 */
@Controller
@RequestMapping("/")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/toQuestionnaire")
    public String toQuestionnaire(QuestionnaireForm form) {
        return "questionnaire";
    }

    @PostMapping("/recommend")
    public String answer(Model model, QuestionnaireForm form) {
        List<Item> itemList = recommendService.recommend(form);
        model.addAttribute("itemList", itemList);

        return "item_list_bousai";
    }
}
