package com.bosssoft.formdesign.domain;

import com.bosssoft.formdesign.comm.CommonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
  * @类描述： 树分类查询
  * @类名称： FormList
  * @创建人： fw
  * @创建时间： 2018-12-26
  */
 
public class FormList implements Serializable {

      private String name;//表单名

      private Date updatetime;//修改时间

      private Date inserttime;//创建时间

      private String classification; //表单对应的分类

      private String id; //表单id

      private String createname;//创建人

      private String userid;//创建人id

      private String typeId;//表单分类ID

      private String template; //对应表单模板

      private String parseTemplate; //转换之后的模板

      private String tableName;//表名

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdatetime() {
        return updatetime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getInserttime() {
        return inserttime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getParseTemplate() {
        return parseTemplate;
    }

    public void setParseTemplate(String parseTemplate) {
        this.parseTemplate = parseTemplate;
    }

    public void preInsert(){

        setId(CommonUtils.getUUID32());
        this.updatetime = new Date();
        this.inserttime = this.updatetime;
    }

}
