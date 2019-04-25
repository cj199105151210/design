package com.bosssoft.formdesign.sqlprovider;

import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.domain.FormList;

import java.io.Console;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
  * @类描述：
  * @类名称： FormSqlProvider
  * @创建人： fw
  * @创建时间： 2018-12-26
  */
 
public class FormSqlProvider {

      public String  queryAllContion(Map<String,Object> map){

          StringBuilder sql = new StringBuilder();
          sql.append("select FORM_NAME name,INSERT_DATE inserttime,UPDATE_DATE updatetime,USER_NAME createname,NAME classification, l.GUID id  from t_form_list l, t_form_type t where l.TYPE_ID = t.GUID ");
          if ( map.size() > 0)
          {
              for ( Map.Entry<String,Object> entry : map.entrySet() )
              {

                  if ("typeId".equals(entry.getKey()) && !entry.getValue().toString().equals("11"))
                   {
                       sql.append("and"+" "+"TYPE_ID ="+" "+"'"+entry.getValue()+"'");
                   }
                   else if ( "FORM_NAME".equals(entry.getKey()) )
                   {
                       sql.append("and "+" "+entry.getKey()+" "+"like"+" "+"'%"+entry.getValue()+"%'");
                   }

              }
              if ( map.containsKey("sortField") )
              {
                  for ( Map.Entry<String,Object> entry : map.entrySet() )
                  {
                      if ( "sortField".equals( entry.getKey() )  )
                      {
                          Map<String,Object> map1  =   JSONObject.parseObject(entry.getValue().toString());
                          if ( map1.get("asc").toString().equals("true") )
                          {
                              sql.append("order by "+" "+map1.get("field")+" "+"asc");
                          }else{
                              sql.append("order by "+" "+map1.get("field")+" "+"desc");
                          }
                      }
                  }
              }else
              {
                  sql.append(" order by inserttime desc , FORM_NAME asc");
              }

          }else {
              sql.append(" order by inserttime desc , FORM_NAME asc");
          }

           return sql.toString();
      }

      //查询字段是否重复
//        public String querySame(Map<String,Object> map){
//            StringBuilder sql = new StringBuilder();
//            sql.append("select USER_NAME createname,INSERT_DATE inserttime,UPDATE_DATE updatetime,FORM_NAME formname,NAME classification, l.GUID id  from t_form_list l, t_form_type t where l.TYPE_ID = t.GUID");
//
//          return"";
//        }




        public String deleteBatch( Map map )
        {
            StringBuilder sql = new StringBuilder();
            List<FormList> list = (List<FormList> ) map.get("list");
            sql.append("DELETE FROM t_form_list WHERE GUID IN (");
            for (  int i = 0; i < list.size(); i++ )
            {
                sql.append("'").append(list.get(i).getId()).append("'");
                if (i < list.size() - 1)
                    sql.append(",");

            }
            sql.append(")");

            return sql.toString();

        }
}
