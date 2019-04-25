package com.bosssoft.formdesign.domain;

 /**
  * @类描述： 树的实体类
  * @类名称： ZTreeVo
  * @创建人： fw
  * @创建时间： 2018-12-24
  */
 
public class ZTreeVo {

    //主键ID
   private String id;
     //父ID
   private String pId;
   //分类名
   private String name;

     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
     }

     public String getpId() {
         return pId;
     }

     public void setpId(String pId) {
         this.pId = pId;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

 }
