package com.bosssoft.formdesign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.comm.AxResult;
import com.bosssoft.formdesign.comm.ParseUtils;
import com.bosssoft.formdesign.comm.PinYinUtils;
import com.bosssoft.formdesign.service.FormInsertService;
import com.bosssoft.formdesign.service.FormListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @类描述：
 * @类名称： FormInsertController
 * @创建人： fw
 * @创建时间： 2019-1-14
 */
@Api(description = "表单增加")
@RestController
@RequestMapping("bdzj")
@Slf4j
public class FormInsertController {

    @Autowired
    private FormInsertService formInsertService;

    @Autowired
    private FormListService formListService;

    @ApiOperation(value = "创建表单表", notes = "创建表单表")
    @PostMapping("createFormTable")
    public AxResult queryAllContion( String data, String id ) {

        AxResult ax = new AxResult();
        Map<String, Object> form = JSON.parseObject(data);
        if (StringUtils.isBlank(id)) {
            String template = form.get("template").toString();
            String parse = form.get("parse").toString();
            parse = ParseUtils.parseTablae(parse);
            parse = ParseUtils.parseUtils(parse, form);
            if (StringUtils.isNotBlank(parse)) {
                ax.setCode(1);
                ax.setData(parse);
            } else {
                ax.setCode(0);
            }
        } else {
            int res = 0;
            String tableName = formInsertService.queryTableName(id);
            if(StringUtils.isBlank(tableName)){
                String formName = formInsertService.queryFormName(id);
                tableName = PinYinUtils.getFirstLetter(formName);
                tableName = formInsertService.checktableName(tableName);
            }
            int num = formInsertService.createFormTabel(form, tableName);
            if (num == 0) {
                String template = form.get("template").toString();
                String parse = form.get("parse").toString();
                parse = ParseUtils.parseTablae(parse);
                parse = ParseUtils.parseUtils(parse, form);
                res = formListService.insertFormTemplent(template, parse, id, tableName);
            }
            if (res > 0) {
                ax.setCode(1);
                ax.setDesc("保存成功");
            } else {
                ax.setCode(0);
                ax.setDesc("保存失败");
            }
        }
        return ax;
    }


}

