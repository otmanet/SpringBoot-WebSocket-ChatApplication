package com.example.AppChats.Service;

import com.example.AppChats.Dto.UserRegisterService;
import com.example.AppChats.Repository.UserRepository;
import com.example.AppChats.model.Role;
import com.example.AppChats.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ImpUserService implements UserService {
    @Autowired
    private UserRepository userRepository;


    public ImpUserService(@Lazy UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
    @Override
    public User save(UserRegisterService userRegisterService) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(userRegisterService.getEmail(),
                passwordEncoder.encode(userRegisterService.getPassword()),
                true,
                userRegisterService.getUsername(),
                Arrays.asList(new Role("USER")));
        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        return null;
    }
}
