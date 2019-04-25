package com.bosssoft.formdesign.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.formMapper.FormInsertMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @类描述： 表单的插入服务端
 * @类名称： FormInsertService
 * @创建人： fw
 * @创建时间： 2019-1-14
 */

@Service
@Transactional
@Slf4j
public class FormInsertService {

    @Autowired
    private FormInsertMapper formInsertMapper;

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    JdbcTemplate jdbcTemplate;


    @Transactional(readOnly = true)
    public String queryFormName( String id ) {

        return formInsertMapper.queryFormName(id);

    }

    @Transactional(readOnly = true)
    public String queryTableName( String id ) {

        return formInsertMapper.queryTableName(id);

    }
    //建表语句的生成
    public int createFormTabel( Map<String,Object> data, String tableName )
    {
        StringBuilder sql = new StringBuilder();
        int num = 0;
        if ( StringUtils.isNotBlank( tableName ) )
        {
            tableName = tableName.toUpperCase();
            sql.append( "create table "+tableName+System.lineSeparator() );
            sql.append("( "+System.lineSeparator());
            String str = data.get("data").toString();
            JSONArray jsonArray = (JSONArray) JSONArray.parse(str);
            for ( int i = 0; i < jsonArray.size(); i++ )
            {
                Map<String,Object> map = JSONObject.parseObject( jsonArray.get(i).toString() );
                if ( map.get("bositype").toString().trim().equals("select") )
                {
                    sql.append(  map.get("model")+" "+"varchar(255) " + "comment "+'"'+map.get("name")+'"' );
                }
                else if( map.get("bositype").toString().trim().equals("text") )
                {
                    sql.append( map.get("model")+" "+"varchar(255) " + "comment "+'"'+map.get("name")+'"' );
                }
                else if( map.get("bositype").toString().trim().equals("textarea")  )
                {
                    sql.append( map.get("model")+" "+"text " + "comment "+'"'+map.get("name")+'"' );
                }
                else if( map.get("bositype").toString().trim().equals("checkboxs")  )
                {
                    sql.append( map.get("model")+" "+"varchar(255) " + "comment "+'"'+map.get("name")+'"' );
                }
                else if( map.get("bositype").toString().trim().equals("radios")  )
                {
                    sql.append( map.get("model")+" "+"varchar(255) " + "comment "+'"'+map.get("name")+'"' );
                }
                else if( map.get("bositype").toString().trim().equals("image")  )
                {
                    sql.append( map.get("model")+" "+"BLOB " + "comment "+'"'+map.get("name")+'"' );
                }
                else if( map.get("bositype").toString().trim().equals("date")  )
                {
                    sql.append( map.get("model")+" "+"varchar(255) " + "comment "+'"'+map.get("name")+'"' );
                }
                    sql.append(" ,"+System.lineSeparator());
            }
            sql.append("GUID "+"varchar(32) "+" NOT NULL "+"primary key "+" comment "+"'主键' ,"+System.lineSeparator());
            sql.append("USERID "+"varchar(32) "+" NOT NULL "+" comment "+"'用户id' ,"+System.lineSeparator());
            sql.append("CREATEDATE "+"timestamp"+" NOT NULL DEFAULT CURRENT_TIMESTAMP "+" comment "+"'创建时间'");
            sql.append(")"+System.lineSeparator());
            sql.append("ENGINE= MYISAM CHARACTER SET utf8;");
            num +=jdbcTemplate.update("DROP TABLE IF EXISTS "+tableName+";");
            num +=jdbcTemplate.update(sql.toString());
            log.info(sql.toString());
        }
        return 0;
    }

    public String checktableName( String tableName ) {
        String name = formInsertMapper.checkTableName(tableName);
        if (StringUtils.isBlank(name)) {
            return tableName + "0000";
        } else {
            int count = Integer.valueOf(name.substring(name.length() - 4));
            String str = String.format("%4d", ++count).replace(" ", "0");
            return tableName + str;
        }
    }

}
