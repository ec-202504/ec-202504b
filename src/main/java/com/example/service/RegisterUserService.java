package com.example.service;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * ユーザーを登録する
     * .
     * @param registerUserForm　ユーザー登録フォーム
     */
    public void registerUser(RegisterUserForm registerUserForm){
        User user = new User();
        user.setName(registerUserForm.getLastName() + " " + registerUserForm.getFirstName());
        user.setEmail(registerUserForm.getEmail());
        user.setPassword(registerUserForm.getPassword());
        user.setZipcode(registerUserForm.getZipcode());
        user.setMunicipalities(registerUserForm.getMunicipalities());
        user.setAddress(registerUserForm.getAddress());
        user.setTelephone(registerUserForm.getTelephone());
        userRepository.insert(user);
    }
}
