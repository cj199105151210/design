package com.bosssoft.formdesign.formMapper;

import com.bosssoft.formdesign.domain.ZTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
  * @类描述： 表单接口操作
  * @类名称： FormTypeMapper
  * @创建人： fw
  * @创建时间： 2018-12-24
  */
 @Mapper
public interface FormTypeMapper {
       final Logger logger = LoggerFactory.getLogger(FormTypeMapper.class);
     @Select({
             "select * from T_FORM_TYPE "
     })
     @Results(id="typeResult",value = {
             @Result(column="GUID", property="id", jdbcType= JdbcType.VARCHAR, id=true),
             @Result(column="NAME", property="name", jdbcType=JdbcType.VARCHAR),
             @Result(column="PARENT_ID", property="pId", jdbcType=JdbcType.VARCHAR)
     })
     List<ZTreeVo> selectAllByType();

     @Delete({"DELETE FROM T_FORM_TYPE WHERE GUID = #{guid, jdbcType=VARCHAR}"})
     int deleteById(String guid);

     @Insert({"insert into T_FORM_TYPE ( guid, PARENT_ID, name ) values ( #{id, jdbcType=VARCHAR}, #{pId, jdbcType=VARCHAR}, #{name, jdbcType=VARCHAR})"})
     int saveByTree( ZTreeVo zv  );

     @Update({"update T_FORM_TYPE SET PARENT_ID = #{pId, jdbcType=VARCHAR}, name = #{name, jdbcType=VARCHAR} WHERE guid = #{id, jdbcType=VARCHAR}"})
     int updateByTree( ZTreeVo zv );

     @Select({"Select NAME FROM T_FORM_TYPE WHERE GUID = #{id,jdbcType=VARCHAR}"})
     String selectName(String id);

     @Select({"Select COUNT(0) FROM T_FORM_LIST WHERE FORM_NAME = #{formName,jdbcType=VARCHAR}"})
     int checkFormName(String formName);
}
