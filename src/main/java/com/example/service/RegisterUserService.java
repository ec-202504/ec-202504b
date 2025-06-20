package com.example.service;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザー情報を操作するサービス.
 */
@Service
@Transactional
public class RegisterUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ユーザーを登録する.
     *
     * パスワードのハッシュ化もここでやる。
     *
     * @param registerUserForm　ユーザー登録フォーム
     */
    public void registerUser(RegisterUserForm registerUserForm){
        User user = new User();
        user.setName(registerUserForm.getLastName() + " " + registerUserForm.getFirstName());
        user.setEmail(registerUserForm.getEmail());
        String encodePassword = passwordEncoder.encode(registerUserForm.getPassword());
        user.setPassword(encodePassword);
        user.setZipcode(registerUserForm.getZipcode());
        user.setPrefecture(registerUserForm.getPrefecture());
        user.setMunicipalities(registerUserForm.getMunicipalities());
        user.setAddress(registerUserForm.getAddress());
        user.setTelephone(registerUserForm.getTelephone());
        userRepository.insert(user);
    }
}
