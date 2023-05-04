package com.elhjuojy.springsecurity_ssms_impl.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Custom UserDetailsService implementation
 */
@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // This is where you should fetch the user from database.
        // We keep it simple to focus on authentication flow.
        System.out.println("We are here users");
        Map<String, String> users = new HashMap<>();
        users.put("martin", passwordEncoder.encode("123"));
        users.put("mehdi", passwordEncoder.encode("123"));
        if (users.containsKey(username)){
            return new User(username, users.get(username), new ArrayList<>());
        }
            System.out.println("Checking the users information");
        throw new UsernameNotFoundException(username);
    }
}