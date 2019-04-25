package com.bosssoft.formdesign.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bosssoft.formdesign.comm.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

/**
 * @类描述：
 * @类名称： SaveDataService
 * @创建人： fw
 * @创建时间： 2019-1-24
 */
@Service
@Transactional
@Slf4j
public class SaveDataService {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class, SQLException.class})
    public int save( String name, Map<String,Object> jsonArray,String userId )
    {
         SQL sql = new SQL();

         sql.INSERT_INTO(name);

        Iterator<Map.Entry<String, Object>> it = jsonArray.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            sql.VALUES(entry.getKey(), "'" +  entry.getValue() + "'");
        }
         sql.VALUES("userId","'"+userId+"'");
         sql.VALUES("GUID", "'"+CommonUtils.getUUID32()+"'");
         int  num  =  jdbcTemplate.update(sql.toString());
         return num;
    }
}
