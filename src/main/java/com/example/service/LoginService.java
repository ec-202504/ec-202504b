package com.example.service;

import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ログインするサービス.
 */
@Service
@Transactional
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    /**
     * メールアドレス、パスワードからユーザーを検索する.
     *
     * @param loginForm フォーム
     * @return ユーザー
     */
    public User login(LoginForm loginForm){
        return userRepository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
    }
}
