package com.example.controller;

import com.example.form.ResisterUserForm;
import com.example.service.ResisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ResisterUserController {

    @Autowired
    private ResisterUserService resisterUserService;

    /**
     * トップページを表示する.
     *
     * @param resisterUserForm ユーザー登録フォーム
     * @return トップページ
     */
    @GetMapping("/topPage")
    public String registerUser(ResisterUserForm resisterUserForm){
        return "top";
    }

    /**
     * ユーザーを登録し、商品一覧に遷移する.
     *
     * @param resisterUserForm ユーザー登録フォーム
     * @param result エラー
     * @param model リクエストスコープ
     * @return 商品一覧（もしくはトップページ）
     */
    @PostMapping("/registerUser")
    public String toResisterUser(
            @Validated ResisterUserForm resisterUserForm
            ,BindingResult result
            , Model model
    ){
        if(!resisterUserForm.getPassword().equals(resisterUserForm.getConfirmPassword())){
            result.rejectValue("confirmPassword","confirmPwdError","パスワードが一致していません");
            return "registerUser";
        }
        if(result.hasErrors()){
            return "registerUser";
        }
        resisterUserService.resisterUser(resisterUserForm);
        return "itemList";
    }

}
