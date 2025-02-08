package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsersRepository repos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repos.findByUsername(username);  // ユーザー名で検索
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // ユーザーが見つかった場合、UserDetails を返す
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())  
                .roles("USER") 
                .build();
    }
}
