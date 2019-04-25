package com.bosssoft.formdesign.domain;

import java.io.Serializable;

/**
  * @类描述：
  * @类名称： UserLogin
  * @创建人： fw
  * @创建时间： 2018-12-20
  */

public class UserLogin implements Serializable{

    //用户名
    private String username;
    //密码
    private String password;
    // 记住用户名和密码
    private Boolean rememberMe;
    //登录类型
    private String loginType;

     public String getUsername() {
         return username;
     }

     public void setUsername(String username) {
         this.username = username;
     }

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     public Boolean getRememberMe() {
         return rememberMe;
     }

     public void setRememberMe(Boolean rememberMe) {
         this.rememberMe = rememberMe;
     }

     public String getLoginType() {
         return loginType;
     }

     public void setLoginType(String loginType) {
         this.loginType = loginType;
     }

 }
