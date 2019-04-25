package com.bosssoft.formdesign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.comm.AxResult;
import com.bosssoft.formdesign.comm.ParseUtils;
import com.bosssoft.formdesign.comm.PinYinUtils;
import com.bosssoft.formdesign.domain.FormList;
import com.bosssoft.formdesign.service.FormInsertService;
import com.bosssoft.formdesign.service.FormListService;
import com.bosssoft.formdesign.service.SaveDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @类描述：
 * @类名称： FormInsertController
 * @创建人： fw
 * @创建时间： 2019-1-14
 */
@Api(description = "表单增加")
@RestController
@RequestMapping("save")
@Slf4j
public class SaveDataController {


    @Autowired
    private FormListService formListService;

    @Autowired
    private SaveDataService saveDataService;

    @ApiOperation(value = "保存表单数据", notes = "保存表单数据")
    @PostMapping("savedata")
    public AxResult save( String data, String id, String userId ) {

        AxResult ax = new AxResult();
        Map<String,Object> jsonObject = JSON.parseObject(data);
        FormList formList = formListService.queryFormByGUID(id);
        String name = formList.getTableName();
        int num = saveDataService.save( name, jsonObject,userId );
        if ( num > 0 )
        {
          ax.setCode(1);
          ax.setDesc("数据插入成功");
        }else
        {
            ax.setCode(1);
            ax.setDesc("数据插入失败");
        }
        return ax;
    }


}

