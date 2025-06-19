package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * ログインフォーム.
 */
public class LoginForm {
    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "メールアドレスの形式が不正です")
    private String email;

    /** パスワード */
    @NotBlank(message = "パスワードを入力してください")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
