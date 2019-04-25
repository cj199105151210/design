package com.bosssoft.formdesign.formMapper;

import com.bosssoft.formdesign.domain.FormList;
import com.bosssoft.formdesign.sqlprovider.FormSqlProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @类描述： 表单查询接口
 * @类名称： FormListMapper
 * @创建人： fw
 * @创建时间： 2018-12-26
 */
@Mapper
public interface FormListMapper {
    final Logger logger = LoggerFactory.getLogger(FormListMapper.class);

    @SelectProvider(type=FormSqlProvider.class, method="queryAllContion")
    List<Map<String,Object>> queryAllContion(Map<String,Object> map);


//    @SelectProvider(type=FormSqlProvider.class, method="querySame")
//    List<Map<String,Object>> querySame(Map<String,Object> map);


    @Delete({"DELETE FROM t_form_list WHERE GUID = #{id, jdbcType=VARCHAR}"  })
    int deleteFormList( String id );

    @Select({"select GUID, FORM_NAME FROM t_form_list WHERE TYPE_ID = #{id, jdbcType=VARCHAR}"})
    List<Map<String, Object>> queryFromByTypeId( String id );

    @Select({"SELECT * FROM t_form_list WHERE GUID = #{id, jdbcType=VARCHAR}"})
    @Results(id = "formMap",value = {
            @Result(column="GUID", property="id", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="TYPE_ID", property="typeId", jdbcType=JdbcType.VARCHAR),
            @Result(column="TEMPLATE", property="template", jdbcType=JdbcType.LONGNVARCHAR),
            @Result(column="PARSE_TEMPLATE", property="parseTemplate", jdbcType=JdbcType.LONGNVARCHAR),
            @Result(column="UPDATE_DATE", property="updatetime", jdbcType=JdbcType.DATE),
            @Result(column="INSERT_DATE", property="inserttime", jdbcType=JdbcType.DATE),
            @Result(column = "FORM_NAME",property = "name",jdbcType = JdbcType.VARCHAR),
            @Result(column="USER_ID", property="userid", jdbcType=JdbcType.VARCHAR),
            @Result(column = "USER_NAME",property = "createname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "TABLE_NAME",property = "tableName",jdbcType = JdbcType.VARCHAR)
    })
    FormList queryFormByGUID(String id);

    @Insert({"insert into t_form_list (guid, type_id, template, update_date, insert_date, form_name, user_name) values" +
            " (#{id, jdbcType=VARCHAR}, #{typeId, jdbcType=VARCHAR},#{template, jdbcType=LONGNVARCHAR},#{updatetime, jdbcType=DATE}," +
            "#{inserttime, jdbcType=DATE},#{name, jdbcType=VARCHAR},#{createname, jdbcType=VARCHAR})"})
    int saveForm(FormList form);

    @DeleteProvider(type=FormSqlProvider.class,method="deleteBatch")
    int deleteForm(@Param("list") List<FormList> list );

    @Update({"update t_form_list set template = #{template, jdbcType=LONGNVARCHAR},PARSE_TEMPLATE = #{parse,jdbcType=LONGNVARCHAR},TABLE_NAME = #{tableName} " +
            "where GUID = #{id, jdbcType = VARCHAR}"})
    int  insertFormTemplent( @Param("template") String template,@Param("parse") String parse, @Param("id")String id, @Param("tableName")String tableName );

    @Select({"select TEMPLATE FROM t_form_list where GUID=#{id, jdbcType = VARCHAR}"})
    String queryHtml( @Param("id") String id );

    @Select({"select PARSE_TEMPLATE FROM t_form_list where GUID=#{id, jdbcType = VARCHAR}"})
    String queryNewHtml(@Param("id") String id );

    @Select({"SELECT TABLE_NAME FROM t_form_list WHERE GUID = #{formId, jdbcType = VARCHAR}"})
    String queryTableName(@Param("formId") String formId );

    @Select({"select * FROM ${tableName} WHERE GUID = #{dataId}"})
    Map<String, Object> queryTableData(@Param("tableName") String tableName, @Param("dataId") String dataId );
}
