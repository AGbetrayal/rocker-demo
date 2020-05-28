package com.example.rockerproductdemo.security.service;

import com.example.rockerproductdemo.emtry.User;
import com.example.rockerproductdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AGbetrayal
 * @date 2019/7/18 11:37
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepository.getByUsername(username);
        if (byUsername == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("VIP"));
        return new org.springframework.security.core.userdetails.User(byUsername.getUsername(),
                byUsername.getPassword(), authorities);
    }
}
