package com.bosssoft.formdesign.formMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
  * @类描述：
  * @类名称： FormInsertMapper
  * @创建人： fw
  * @创建时间： 2019-1-14
  */

@Mapper
public interface FormInsertMapper {
    final Logger logger = LoggerFactory.getLogger(FormListMapper.class);
     @Select({"select FORM_NAME FROM t_form_list WHERE GUID = #{id, jdbcType=VARCHAR}"})
     String queryFormName(String id);

    @Select({"select TABLE_NAME FROM t_form_list WHERE GUID = #{id, jdbcType=VARCHAR}"})
    String queryTableName(String id);

    @Select({"select MAX(TABLE_NAME) FROM t_form_list WHERE TABLE_NAME LIKE CONCAT(#{tableName}, '%')"})
    String checkTableName(String tableName);
}
