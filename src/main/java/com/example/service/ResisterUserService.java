package com.example.service;

import com.example.domain.User;
import com.example.form.ResisterUserForm;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザー情報を操作するサービス.
 */
@Service
@Transactional
public class ResisterUserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * ユーザーを登録する
     * .
     * @param resisterUserForm　ユーザー登録フォーム
     */
    public void resisterUser(ResisterUserForm resisterUserForm){
        User user = new User();
        user.setName(resisterUserForm.getLastName() + " " + resisterUserForm.getFirstName());
        user.setEmail(resisterUserForm.getEmail());
        user.setPassword(resisterUserForm.getPassword());
        user.setZipcode(resisterUserForm.getZipcode());
        user.setMunicipalities(resisterUserForm.getMunicipalities());
        user.setAddress(resisterUserForm.getAddress());
        user.setTelephone(resisterUserForm.getTelephone());
        userRepository.insert(user);
    }
}
