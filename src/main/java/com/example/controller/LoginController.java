package com.example.controller;

import com.example.common.EarthquakeAndVolcanoFeed;
import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.service.LoginService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * ログインに関連するコントローラ.
 */
@Controller
@RequestMapping("")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private ServletContext application;

    /**
     * ログイン画面を表示する.
     *
     * @param loginForm フォーム
     * @return ログイン画面
     */
    @GetMapping("/")
    public String login(LoginForm loginForm, Model model){
        List<EarthquakeAndVolcanoFeed.VolcanoReport> volcanoReports =
                (List<EarthquakeAndVolcanoFeed.VolcanoReport>) application.getAttribute("volcanoReports");

        List<EarthquakeAndVolcanoFeed.EarthquakeReport> earthquakeReports =
                (List<EarthquakeAndVolcanoFeed.EarthquakeReport>) application.getAttribute("earthquakeReports");

        model.addAttribute("volcanoReports", volcanoReports);
        model.addAttribute("earthquakeReports", earthquakeReports);
        return "loginBosai";
    }

    /**
     * ログインする.
     *
     * @param loginForm フォーム
     * @param result エラー
     * @param model リクエストスコープ
     * @return 商品一覧（もしくはログイン画面）
     */
    @PostMapping("/toLogin")
    public String toLogin(
            @Validated LoginForm loginForm
            , BindingResult result
            , Model model
            ){
        if(result.hasErrors()){
            return "loginBosai";
        }
        User user = loginService.login(loginForm);
        if(user == null){
            return "loginBosai";
        }
        return "item_list_pizza";
    }
}
