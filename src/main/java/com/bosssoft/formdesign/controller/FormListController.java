package com.bosssoft.formdesign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosssoft.formdesign.comm.AxResult;
import com.bosssoft.formdesign.comm.CommonUtils;
import com.bosssoft.formdesign.domain.FormList;
import com.bosssoft.formdesign.domain.ZTreeVo;
import com.bosssoft.formdesign.page.DataTablePageUtil;
import com.bosssoft.formdesign.service.FormListService;
import com.bosssoft.formdesign.service.FormTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @类描述： 表单分类查询
 * @类名称： FormListController
 * @创建人： fw
 * @创建时间： 2018-12-26
 */
@Api(description = "表单列表")
@RestController
@RequestMapping("bdlb")
@Slf4j
public class FormListController {

    @Autowired
    private FormListService formListService;

    @Autowired
    FormTypeService formTypeService;

    @ApiOperation(value = "获取所有的表单分类", notes = "获取所有的表单分类")
    @PostMapping("queryAllForm")
    public AxResult queryAllContion(HttpServletRequest request, String data) {
        AxResult ax = new AxResult();
        Map<String, Object> map = JSONObject.parseObject(data);
        DataTablePageUtil<Map<String, Object>> dataTable = new DataTablePageUtil<>(request);
        PageHelper.startPage(dataTable.getStart(), dataTable.getLength());
        List<Map<String, Object>> list = formListService.queryAllContion(map);
        try {

            dataTable = CommonUtils.getReturnDataTable(dataTable, list);

        } catch (Exception e) {
            ax.setCode(0);
            ax.setDesc("查詢失敗");
            return ax;
        }
        ax.setData(dataTable);
        ax.setCode(1);
        ax.setDesc("查詢成功");
        return ax;

    }

    @ApiOperation(value = "删除对应的表单", notes = "删除对应的表单")
    @PostMapping("deleteFormList")
    public AxResult deleteFormList(String id) {

        AxResult ax = new AxResult();

        int num = formListService.deleteFormList(id);
        if (num > 0) {
            ax.setDesc("删除成功");
            ax.setCode(1);
        } else {
            ax.setDesc("删除失败");
            ax.setCode(0);
        }

        return ax;

    }

    @ApiOperation(value = "删除选中的表单", notes = "删除选中的表单")
    @PostMapping("deleteForm")
    public AxResult deleteForm(String id) {

        AxResult ax = new AxResult();
        List<FormList> list = JSONObject.parseArray(id, FormList.class);
        int num = formListService.deleteForm(list);
        if (num > 0) {
            ax.setDesc("删除成功");
            ax.setCode(1);
        } else {
            ax.setDesc("删除失败");
            ax.setCode(0);
        }
        return ax;
    }

    @ApiOperation(value = "查询对应的表单", notes = "查询对应的表单")
    @PostMapping("queryFromByTypeId")
    public AxResult queryFromByTypeId(String id) {

        AxResult ax = new AxResult();

        List<Map<String, Object>> list = formListService.queryFromByTypeId(id);
        if (list.size() > 0) {
            ax.setCode(1);
            ax.setData(list);
        } else {
            ax.setCode(0);
            ax.setDesc("没有对应的表单");
        }

        return ax;

    }

    @ApiOperation(value = "查询对应的html", notes = "查询对应的html")
    @PostMapping("queryHtml")
    public AxResult queryHtml(String id) {

        AxResult ax = new AxResult();
        String html = formListService.queryHtml(id);
        ax.setCode(0);
        ax.setData(id);
        ax.setDesc(html);
        return ax;
    }

    @ApiOperation(value = "保存表单", notes = "保存表单")
    @PostMapping("saveForm")
    public AxResult saveForm(HttpServletRequest request, String form) {

        AxResult ax = new AxResult();

        FormList formList = JSON.parseObject(form, FormList.class);
        if (StringUtils.isNotBlank(formList.getId())) {
            FormList formList1 = formListService.queryFormByGUID(formList.getId());
            formList.setTemplate(formList1.getTemplate());
        }
        formList.preInsert();
        formList.setClassification(formTypeService.selectName(formList.getTypeId()));
        int num = formListService.saveOrUpdateForm(formList);
        log.info("输出的结果是" + formList);
        if (num > 0) {
            ax.setDesc("保存成功");
            ax.setData(formList.getId());
            ax.setCode(1);
        } else {
            ax.setDesc("保存失败");
            ax.setCode(0);
        }
        return ax;

    }

    @ApiOperation(value = "表单名称重复验证", notes = "保存表单")
    @PostMapping("checkFormName")
    public AxResult checkFormName(HttpServletRequest request, String formName) {

        AxResult ax = new AxResult();
        if (formTypeService.checkFormName(formName)) {
            ax.setDesc("表单名重复");
            ax.setCode(0);
        } else {
            ax.setDesc("表单名未重复");
            ax.setCode(1);
        }
        return ax;

    }

    @ApiOperation(value = "查询对应的html", notes = "查询对应的html")
    @PostMapping("queryNewHtml")
    public AxResult queryNewHtml(String formId, String dataId, String userId) {

        AxResult ax = new AxResult();
        String html = formListService.queryNewHtml(formId);

        if (!"preview".equals(dataId)) {

            // 将页面代码截断为控件
            List<String> htmlList = Arrays.asList(html.split("<"));

            // 根据前台传来数据主键，来取得该条数据
            Map<String, Object> tableData = formListService.queryFormData(formId, dataId, userId);

            for (Map.Entry<String, Object> entry : tableData.entrySet()) {
                // 将数据的值插入到html的字符串中
                String key = "ng-model=\"" + entry.getKey() + "\"";
                for (int i = 0; i < htmlList.size(); i++) {
                    String htmlTemp = htmlList.get(i);
                    if (htmlTemp.indexOf(key) > -1) {
                        // 当前map储存的值找到对应控件
                        // 取得当前控件的bositype属性值
                        String bositype = "";
                        int startIndex = htmlTemp.indexOf("bositype=\"");
                        if (startIndex > -1) {
                            String tempStr = htmlTemp.substring(startIndex + 10);
                            int endIndex = tempStr.indexOf("\"");
                            bositype = tempStr.substring(0, endIndex);
                        }

                        StringBuffer htmlBuffer = new StringBuffer(htmlTemp);
                        // 不同控件对应不同赋值方法
                        switch (bositype) {
                            case "text":
                                // 单行文本
                                htmlBuffer.insert(htmlTemp.indexOf(key), "value=\"" + entry.getValue().toString() + "\" ");
                                htmlList.set(i, htmlBuffer.toString());
                                break;
                            case "textarea":
                                // 多行文本
                                htmlBuffer.append(entry.getValue().toString());
                                htmlList.set(i, htmlBuffer.toString());
                                break;
                            case "":
                                // 下拉菜单
                                int j = i + 1;
                                String options = htmlList.get(j);
                                while (options.indexOf("option") == 0) {
                                    String value = options.substring(14, options.length() - 3);
                                    if (value.equals(entry.getValue().toString())) {
                                        options = options.replace(">", " selected=\"selected\">");
                                        htmlList.set(j, options);
                                    }
                                    j = j + 2;
                                    options = htmlList.get(j);
                                }
                                break;
                            case "radios":
                                // 单选框
                                String value = "";
                                int start = htmlTemp.indexOf("value=\"");
                                if (start > -1) {
                                    String tempStr = htmlTemp.substring(start + 7);
                                    int end = tempStr.indexOf("\"");
                                    value = tempStr.substring(0, end);
                                    if (value.equals(entry.getValue().toString())) {
                                        htmlTemp = htmlTemp.replace("/>", " checked/>");
                                        htmlList.set(i, htmlTemp);
                                    }
                                }
                                break;
                            case "checkboxs":
                                // 复选框
                                value = "";
                                start = htmlTemp.indexOf("value=\"");
                                if (start > -1) {
                                    String tempStr = htmlTemp.substring(start + 7);
                                    int end = tempStr.indexOf("\"");
                                    value = tempStr.substring(0, end);
                                    if (entry.getValue().toString().indexOf(value) > -1) {
                                        htmlTemp = htmlTemp.replace("/>", " checked/>");
                                        htmlList.set(i, htmlTemp);
                                    }
                                }
                                break;
                            case "img":
                                // 图片
                                value = "";
                                try {
                                    value = new String((byte[]) entry.getValue(), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    ax.setCode(1);
                                    ax.setDesc("图片获取失败");
                                    return ax;
                                }
                                htmlTemp = htmlTemp.replace(" ax/css/img_default.png", value);
                                htmlList.set(i, htmlTemp);
                                break;
                            case "date":
                                // 时间
                                htmlBuffer.insert(htmlTemp.indexOf(key), "value=\"" + entry.getValue().toString() + "\" ");
                                htmlList.set(i, htmlBuffer.toString());
                                break;
                        }
                    }
                }
            }
            html = StringUtils.join(htmlList.toArray(), "<");
        }
        ax.setCode(0);
        ax.setData(formId);
        ax.setDesc(html);
        return ax;
    }
}
