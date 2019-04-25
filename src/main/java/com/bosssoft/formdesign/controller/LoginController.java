package com.bosssoft.formdesign.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.comm.AxResult;
import com.bosssoft.formdesign.comm.HttpUtils;
import com.bosssoft.formdesign.config.Config;
import com.bosssoft.formdesign.domain.UserLogin;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
  * @类描述： 登录相关操作
  * @类名称： LoginController
  * @创建人： fw
  * @创建时间： 2018-12-19
  */
 @Api(description = "用户登录相关操作")
 @RestController

public class LoginController {

     private  static final Logger logger = LoggerFactory.getLogger(LoginController.class);

     @Autowired
     Config config;

    /**
     * 该接口采用了熔断机制
     * @param userLogin
     * @return
     */
    @ApiOperation(value = "用户登录接口",notes = "用户登录接口")
    @PostMapping("userLogin")
    @HystrixCommand(fallbackMethod = "userLoginError",
            //10个核心线程池,超过20个的队列外的请求被拒绝;
            // 当一切都是正常的时候，
            // 线程池一般仅会有1到2个线程激活来提供服务
            threadPoolProperties = {
               @HystrixProperty(name ="coreSize", value = "10"),
               @HystrixProperty(name = "maxQueueSize", value = "100"),
               @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
            },
            commandProperties = {
                 //命令执行超时时间
                @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
                 //若干10s一个窗口内失败三次, 则达到触发熔断的最少请求量
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                //断路30s后尝试执行, 默认为5s
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
            }
    )
    public Object userLogin(HttpServletRequest request, UserLogin userLogin){

        AxResult result = new AxResult();
        String rs = "";
        try
        {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

            map.add("ciphername",userLogin.getUsername());
            map.add("ciphercode",userLogin.getPassword());
            map.add("rememberMe",userLogin.getRememberMe());


            rs = HttpUtils.doPost(config.getLoginUrl(), map, true);

            if ( StringUtils.isNotBlank(rs))
            {
                JSONObject jsonObject = JSONObject.parseObject(rs);
                logger.info("返回的数据结果是"+jsonObject);

                Integer status = jsonObject.getInteger("status");


                if ( status != 200 )
                {
                   result.setCode(0);
                   result.setDesc(jsonObject.getString("message"));

                   return result;
                }
                else
                {
                    result.setCode(1);
                    result.setDesc(jsonObject.getString("message"));
                    JSONObject user = jsonObject.getJSONObject("user");
                    result.setData(user);

                }
            }


        }
        catch (Exception e)
        {
             return rs;
        }
        return result;

    }

    /**
     * 获取菜单接口
     * @param request
     * @param data
     * @return
     */
    @ApiOperation(value="获取菜单接口",notes="获取菜单接口")
    @ApiImplicitParams(value = {
                       @ApiImplicitParam(name = "data",value = "用户参数",required = true,paramType = "query",dataType = "String")
    })
    @HystrixCommand(fallbackMethod = "getMenuDataError",
            //10个核心线程池,超过20个的队列外的请求被拒绝;
            // 当一切都是正常的时候，
            // 线程池一般仅会有1到2个线程激活来提供服务
            threadPoolProperties = {
                    @HystrixProperty(name ="coreSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "100"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
            },
            commandProperties = {
                    //命令执行超时时间
                    @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
                    //若干10s一个窗口内失败三次, 则达到触发熔断的最少请求量
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    //断路30s后尝试执行, 默认为5s
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
            }
    )
    @PostMapping("getMenuData")
    public Object getMenuData(HttpServletRequest request,
                              @RequestParam(value = "data",required = false)String data) {
               AxResult result = new AxResult();
               String userCode = JSONObject.parseObject(data).getString("userCode");
                if ( userCode == null )
                {
                    result.setCode(0);
                    result.setDesc("说明用户没有登录，前期暂不处理这个情况");
                    return result;
                }
               MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
               params.add("userCode",userCode);
               String doPost = HttpUtils.doPost(config.getMenuUrl(), params, true);
               if ( StringUtils.isNotBlank(doPost) )
               {
                   JSONObject menu = transMenu(doPost);
                   result.setDesc("获取菜单成功");
                   result.setCode(1);
                   result.setData(menu);
                   return result;
               }
               else
               {
                   return doPost;
               }

    }

    public Object userLoginError(HttpServletRequest request,UserLogin userLogin){

        AxResult result = new AxResult();
        result.setDesc("登录人数太多！请稍后重试");
        result.setCode(2);
        return result;

    }

    public Object getMenuDataError(HttpServletRequest request, String data){

        AxResult result = new AxResult();
        result.setDesc("登录人数太多！请稍后重试");
        result.setCode(2);
        return result;

    }

    /**
     * 解析菜单
     * @param str
     * @return
     */
    private JSONObject transMenu( String str ){

        JSONArray array = JSONObject.parseArray(str);
        Map<String, JSONObject> parentMap = new HashMap<>();
        Map<String, List<JSONObject>> sonMapData = new HashMap<>();
        //
        for ( int i = 0; i < array.size(); i++ )
        {
            JSONObject item = array.getJSONObject(i);
            Boolean isParent = item.getBoolean("isParent");
            String name = item.getJSONObject("treeData").getString("menuName");
            //判断是否存在父节点
            if ( isParent != null )
            {
                //判断是否为父节点
                  if ( isParent )
                  {
                      //添加到父节点集合中去
                      JSONObject parentItem = new JSONObject();
                      parentItem.put("name",name);
                      parentItem.put("icon","fa-paste");
                      String id = item.getString("treeId");
                      parentMap.put( id, parentItem );

                  }else {
                          //获取子标签
                          String parentId = item.getString("treeParentId");
                          JSONObject sonItem = new JSONObject();
                          sonItem.put("page_id", 0);
                          sonItem.put("name", name);
                          sonItem.put("url", item.getJSONObject("treeData").getString("menuUrl"));
                          if ( sonItem.containsKey(parentId) )
                          {
                              sonMapData.get(parentId).add(sonItem);
                          }
                          else
                          {
                              List<JSONObject> sonItemList = new ArrayList<>();
                              sonItemList.add(sonItem);
                              sonMapData.put(parentId,sonItemList);
                          }
                  }
            }
            else
            {
                continue;
            }

        }

        //开始组合数据
        JSONObject menuData = new JSONObject();
        menuData.put("document_title", config.getDocument_title());
        menuData.put("title", config.getTitle());

        List<JSONObject> itemMenuList = new ArrayList<>();

        for ( Map.Entry<String, JSONObject> entry : parentMap.entrySet() )
        {
            String id = entry.getKey();
            JSONObject parentData =  entry.getValue();
            List<JSONObject> sunData = sonMapData.get(id);

            parentData.put("items",sunData);

            itemMenuList.add(parentData);
        }
        menuData.put("menu",itemMenuList);

        return  menuData;
    }



}
