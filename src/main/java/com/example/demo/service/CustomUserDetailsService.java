package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.UserEntity;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails  loadUserByUsername(String username) throws UsernameNotFoundException {
        //find user from database
        UserEntity user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not Found!" + username));
                return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    // .authorities(user.getRole())
                    .authorities("ROLE_" + user.getRole())
                    .build();
    }

}
