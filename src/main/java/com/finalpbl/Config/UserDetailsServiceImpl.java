package com.finalpbl.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.finalpbl.Model.User;
import com.finalpbl.Repository.UserRepository;


@Component
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(null);
        if(user != null)
        {
            return new UserDetailsImpl(user); 
        }
        throw new UsernameNotFoundException("User not found");
    }
}
