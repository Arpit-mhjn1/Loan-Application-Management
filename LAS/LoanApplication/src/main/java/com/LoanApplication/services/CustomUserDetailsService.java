package com.LoanApplication.services;

import java.util.Optional;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.LoanApplication.model.User;
import com.LoanApplication.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> users = userRepository.findByEmail(email);
        if (users.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                users.get().getEmail(),
                users.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + users.get().getRole()))
        );
    }
}
