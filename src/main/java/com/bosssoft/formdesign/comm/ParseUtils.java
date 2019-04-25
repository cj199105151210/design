package com.bosssoft.formdesign.comm;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
  * @类描述： 转换成对应字段
  * @类名称： ParseUtils
  * @创建人： fw
  * @创建时间： 2019-1-15
  */
public class ParseUtils {

    public static String parseUtils( String content ,Map<String, Object> form )
    {
         JSONArray jsonArray =  JSON.parseArray(form.get("data").toString());
         for ( int i = 0; i < jsonArray.size(); i++  )
         {
            Map<String,Object> map = JSON.parseObject(jsonArray.get(i).toString());
            if ( map.get("bositype").equals("date") )
            {
                String str = "<input  name = " + map.get("name") + " class=\"form-control ax-input-select " + map.get("value") + "\" "
                        + "ax-laydate "+"style = width:"+ map.get("bosidatelength")+"px" + " id = " + map.get("model")+" ng-model=\"" + map.get("model")+ "\"" + " bositype=\"date\""+" readonly />" + "<i class=\"fa fa-calendar\" style=\"padding-right:10px;margin-left:-20px;pointer-events:none; \"></i>";
                content = content.toString().replace(map.get("content").toString(),str);
            }
            if (  map.get("bositype").equals("image") )
            {
                String str2 = "<div id=\"uploaderInput\" style=\"border:1px solid #D0D0D0;width:"+0+"px;"+"height:"+0+"px;margin-bottom:5px\" +\"visibility:hidden\"+ align=\"center\">\n" +
                          "</div>"+
                        "<div  >\n" +
                        "<img  id=\"uploaderPreview\" onclick=\"uploaderClick()\" bositype=\"img\" width=\"100px\" height=\"178px\" ng-model=\"" + map.get("model") + "\" style=\"cursor:pointer\" src = \" ax/css/img_default.png\" >\n" +
                        "\t\t\t\t\t<p><font color=\"5281b5\">建议图片宽高比1:1.78，如750*1334</font></p></div>\n" +
                        "\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"showImage()\" ng-show=\""+map.get("model")+"\" class=\"ng-binding ng-hide\">&nbsp;点击预览：{{"+ map.get("model") +"}}</a>";
                   content = content.replace(map.get("content").toString(),str2);
            }

         }
        return content;
    }

    public static String parseTablae( String content  )
    {
        String regx = "<table>";
        String str2 = "<table class=\"table table-striped table-bordered\">";
        content = content.replace(regx,str2);

        return content;
    }


    
}
