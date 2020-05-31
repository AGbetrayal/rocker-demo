package com.example.rockerproductdemo.security.service;

import com.example.rockerproductdemo.emtry.User;
import com.example.rockerproductdemo.repository.UserRepository;
import com.example.rockerproductdemo.security.valueobject.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        User byUsername = userRepository.getByUserName(username);
        if (byUsername == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("VIP"));
        return new org.springframework.security.core.userdetails.User(byUsername.getUserName(),
                byUsername.getPassWord(), authorities);
    }

    public UserView getUserByUserName(String userName){

        UserView userView = new UserView();
        User user = userRepository.getByUserName(userName);
        userView.setUserName(user.getUserName());
//        List<String> roleCodes = new ArrayList<>();
//        user.getRoles().stream().forEach(role -> roleCodes.add(role.getRoleCode()));
//        userView.setRoleCodes(roleCodes);
        return userView;
    }

    public UserDetails getUserDetailByUserName(String username){

        User user = this.userRepository.getByUserName(username);

        if(user == null){
            //throw exception inform front end not this user
            throw new UsernameNotFoundException("user + " + username + "not found.");
        }

//        List<String> roleList = this.userRepository.queryUserOwnedRoleCodes(username);
        List<String> roleList = new ArrayList<>();
        roleList.add("VIP");
        List<GrantedAuthority> authorities = roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails
                .User(username,user.getPassWord(),authorities);
    }
}
