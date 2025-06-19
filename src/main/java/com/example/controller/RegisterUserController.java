package com.example.controller;

import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザー情報を操作するコントローラ.
 */
@Controller
@RequestMapping("")
public class RegisterUserController {

    @Autowired
    private RegisterUserService registerUserService;

    /**
     * トップページを表示する.
     *
     * @param registerUserForm ユーザー登録フォーム
     * @return トップページ
     */
    @GetMapping("/topPage")
    public String registerUser(RegisterUserForm registerUserForm){
        return "topBosai";
    }

    /**
     * ユーザーを登録し、商品一覧に遷移する.
     *
     * @param registerUserForm ユーザー登録フォーム
     * @param result エラー
     * @param model リクエストスコープ
     * @return 商品一覧（もしくはトップページ）
     */
    @PostMapping("/registerUser")
    public String toResisterUser(
            @Validated RegisterUserForm registerUserForm
            ,BindingResult result
            , Model model
    ){
        if(!registerUserForm.getPassword().equals(registerUserForm.getConfirmPassword())){
            result.rejectValue("confirmPassword","confirmPwdError","パスワードが一致していません");
            return "topBosai";
        }
        if(result.hasErrors()){
            return "topBosai";
        }
        registerUserService.registerUser(registerUserForm);
        return "item_list_bousai";
    }

}
