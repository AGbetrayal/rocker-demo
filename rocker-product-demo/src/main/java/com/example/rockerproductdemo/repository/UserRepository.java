package com.example.rockerproductdemo.repository;

import com.example.rockerproductdemo.emtry.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AGbetrayal
 * @date 2019/7/18 11:34
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User getByUsername(String username);
}
