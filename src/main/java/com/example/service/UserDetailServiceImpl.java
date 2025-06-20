package com.example.service;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailServiceImpl implements UserDetailsService {
    /**
     * DBから情報を得るためのリポジトリ
     */
    @Autowired
    private UserRepository userRepository;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String) DBから検索をし、ログイン情報を構成して返す。
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email:" + email);
        User user = userRepository.findByMailAddress(email);
        if (user == null) {
            throw new UsernameNotFoundException("そのEmailは登録されていません。");
        }
        // 権限付与の例
        Collection<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
        return new LoginUser(user, authorityList);
    }
}
