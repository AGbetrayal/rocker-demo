package com.example.rockerproductdemo.emtry;

import lombok.Data;

import javax.persistence.*;

/**
 * @author AGbetrayal
 * @date 2019/7/18 11:30
 */
@Entity
@Table(name = "xc_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String passWord;
    @Column(name = "salt")
    private String salt;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private String status;
}
