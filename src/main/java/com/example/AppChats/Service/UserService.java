package com.example.AppChats.Service;

import com.example.AppChats.Dto.UserRegisterService;
import com.example.AppChats.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;



public interface UserService extends UserDetailsService {
    /*
     * UserDetailsService :Core interface which loads user-specific data. It is used
     * throughout the framework
     * as a user DAO and is the strategy used by the DaoAuthenticationProvider .
     */
    User save(UserRegisterService userRegisterService);


}
