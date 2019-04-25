package com.bosssoft.formdesign.comm;

 /**
  * @类描述： 返回的数据的封装
  * @类名称： AxResult
  * @创建人： fw
  * @创建时间： 2018-12-19
  */
 
public class AxResult {

    private Integer code; //返回的code

     private Object data; //返回的数据

     private String desc; //返回的结果描述

     public Integer getCode() {
         return code;
     }

     public void setCode(Integer code) {
         this.code = code;
     }

     public Object getData() {
         return data;
     }

     public void setData(Object data) {
         this.data = data;
     }

     public String getDesc() {
         return desc;
     }

     public void setDesc(String desc) {
         this.desc = desc;
     }
 }
