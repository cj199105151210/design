package com.bosssoft.formdesign.controller;

import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.comm.AxResult;
import com.bosssoft.formdesign.domain.ZTreeVo;
import com.bosssoft.formdesign.service.FormTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
  * @类描述： 表单操作
  * @类名称： FormType
  * @创建人： fw
  * @创建时间： 2018-12-24
  */

@Api(description = "表单分类操作")
@RestController
@RequestMapping("bdfl")
public class FormTypeController {

    @Autowired
    FormTypeService formTypeService;

    @ApiOperation(value = "获取所有的表单分类", notes = "获取所有的表单分类")
    @PostMapping("queryAllType")
    public List<ZTreeVo> queryAllContion() {


        return  formTypeService.selectAllByType();

    }

    @ApiOperation(value = "删除对应表单", notes = "获取所有的表单")
    @PostMapping("deleteTypeById")
    public AxResult deleteTypeById(@RequestParam("guid") String guid) {

        AxResult ax = new AxResult();
        int num = formTypeService.deleteTypeById(guid);
        if ( num > 0 )
        {
            ax.setCode(1);
            ax.setDesc("删除成功");
        }
        else
        {
            ax.setCode(1);
            ax.setDesc("删除失败");
        }
        return  ax;

    }

    @ApiOperation(value = "保存和修改表单单分类", notes = "保存和修改表单单分类")
    @PostMapping("saveOrUpdateByTree")
    @ResponseBody
    public AxResult saveOrUpdateByTree(  String zTreeVo )
    {
        int num;
        ZTreeVo zv =   JSONObject.parseObject(zTreeVo,ZTreeVo.class);
        AxResult ax = new AxResult();
        if ( StringUtils.isBlank(zv.getId()) )
        {
           num = formTypeService.saveByTree(zv);
           if (  num > 0 )
           {
               ax.setDesc("操作成功");
               ax.setCode(1);
           }
           else
           {
               ax.setDesc("操作失败");
               ax.setCode(0);
           }

        }else
        {
            num = formTypeService.updateByTree(zv);
            if (  num > 0 )
            {
                ax.setDesc("操作成功");
                ax.setCode(1);
            }
            else
            {
                ax.setDesc("操作失败");
                ax.setCode(0);
            }
        }

        return  ax;

    }


}
