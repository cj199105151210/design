package com.bosssoft.formdesign.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//  * @类描述： 登录的配置文件
//  * @类名称： Config
//  * @创建人： fw
//  * @创建时间： 2018-12-19

@Component
@ConfigurationProperties(prefix = "service")
public class Config {

    //用户登陆地址
   private String loginUrl;
  //菜单路径
   private String menuUrl;
   //文件名称
    private String document_title;
    //标题
    private String title;

    public String getDocument_title() {
        return document_title;
    }

    public void setDocument_title(String document_title) {
        this.document_title = document_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLoginUrl() {
       return loginUrl;
   }

   public void setLoginUrl(String loginUrl) {
      this.loginUrl = loginUrl;
   }

    public String getMenuUrl() {
       return menuUrl;
   }

    public void setMenuUrl(String menuUrl) {
       this.menuUrl = menuUrl;
   }
}
