package com.bosssoft.formdesign.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
  * @类描述：
  * @类名称： WebMvcConfiguration
  * @创建人： fw
  * @创建时间： 2018-12-19
  */
@Configuration
public class WebMvcConfiguration  extends WebMvcConfigurerAdapter {

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
