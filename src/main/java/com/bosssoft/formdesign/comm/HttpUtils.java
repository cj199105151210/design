package com.bosssoft.formdesign.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
  * @类描述： 发送http请求工具
  * @类名称： HttpUtils
  * @创建人： fw
  * @创建时间： 2018-12-20
  */

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post 请求
     * @param url
     * @param map
     * @param isForm
     * @return
     */
    public static  String doPost(String url, MultiValueMap<String, Object> map, Boolean isForm)
    {
        // 放回结果
        String result = "" ;
        //rest的编码请求
        RestTemplate restTemplate = new RestTemplate();
       //设置头部的编码格式
        HttpHeaders headers = new HttpHeaders();
       MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
       headers.setContentType(type);
        if ( isForm )
        {
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            //将请求头部合成一个请求
            HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<MultiValueMap<String,Object>>(map,headers);
            ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            result = entity.getBody();

        }else
        {
            HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<MultiValueMap<String,Object>>(map,headers);
            ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            result = entity.getBody();
        }

        return result;
    }

    /**
     * get请求
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String,Object> params){

        //返回结果
        String result = "";
        //rest的编码请求
        RestTemplate restTemplate = new RestTemplate();
        if ( params != null )
        {
            url = getGetUrl(url+"?", params);
        }
        ResponseEntity<String> entity =  restTemplate.getForEntity(url,String.class);

        return entity.getBody();
    }

    /**
     * 对url进行拼接
     * @param url
     * @param params
     * @return
     */
    private static String getGetUrl(String url,Map<String,Object> params){

        if ( params != null )
        {
            Set<Map.Entry<String,Object>> entrySet = params.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

            while ( iterator.hasNext() )
            {
                Map.Entry<String, Object> entry = iterator.next();
                url = url.concat(entry.getKey()).concat("=").concat(entry.getValue().toString());
                if (iterator.hasNext()){
                    url = url.concat("&");
                }
            }
        }

        return url;
    }

    public static void main(String[] args) {

//
//        String url = "http://localhost:8080/appframe-web/u/submitLogin.do";
//
//        MultiValueMap<String,Object> params =  new LinkedMultiValueMap<String, Object>();
//        params.add("ciphername","fhz1");
//        params.add("ciphercode","c4ca4238a0b923820dcc509a6f75849b");
//        params.add("rememberMe","false");
//        String result = doPost(url,params,true);
//        System.out.println(result);

        String menuDataUrl = "http://localhost:8080/appframe-web/platform/appframe/afauser/getMenuRelation.do";

        MultiValueMap<String,Object> param1 = new LinkedMultiValueMap<String, Object>();

        param1.add("userCode","admin");

        String menuData = doPost(menuDataUrl,param1,true);

        System.out.println("获取到菜单信息:"+menuData);


    }


}
