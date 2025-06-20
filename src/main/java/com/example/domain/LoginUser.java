package com.example.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends org.springframework.security.core.userdetails.User {
    /**
     * ユーザー
     */
    private final com.example.domain.User user;

    /**
     * 通常の管理者情報に加え、認可用ロールを設定する.
     *
     * @param user ユーザー
     * @param authorityList 権限情報が入ったリスト
     */
    public LoginUser(com.example.domain.User user, Collection<GrantedAuthority> authorityList) {
        super(user.getEmail(), user.getPassword(), authorityList);
        this.user = user;
    }

    /**
     * ユーザー情報を返す.
     *
     * @return ユーザー情報
     */
    public com.example.domain.User getUser() {
        return user;
    }
}
