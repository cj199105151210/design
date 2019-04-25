package com.bosssoft.formdesign.service;

import com.bosssoft.formdesign.domain.FormList;
import com.bosssoft.formdesign.formMapper.FormListMapper;
import com.bosssoft.formdesign.formMapper.FormTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @类描述： 表单分类服务类
 * @类名称： FormListService
 * @创建人： fw
 * @创建时间： 2018-12-26
 */

@Service
@Transactional
@Slf4j
public class FormListService {

    @Autowired
    private FormListMapper formListMapper;


    @Transactional(readOnly = true)
    public   String  queryHtml( String id ){

        return formListMapper.queryHtml(id);
    }

    @Transactional(readOnly = true)
    public   String  queryNewHtml( String id ){

        return formListMapper.queryNewHtml(id);
    }

    @Transactional(readOnly = true)
    public   List<Map<String,Object>>  queryAllContion(Map<String,Object> map){


        return formListMapper.queryAllContion(map);

    }
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class, SQLException.class})
    public  int deleteFormList( String id )
    {
        return formListMapper.deleteFormList(id);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class, SQLException.class})
    public  int deleteForm( List<FormList> list  )
    {
        return formListMapper.deleteForm(list);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> queryFromByTypeId( String id )
    {

        return formListMapper.queryFromByTypeId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class, SQLException.class})
    public int  saveOrUpdateForm( FormList form )
    {

        return formListMapper.saveForm(form);
    }

    @Transactional(readOnly = true)
    public FormList queryFormByGUID(String id)
    {
        return formListMapper.queryFormByGUID(id);
    }

    /**
     * 表单模板的插入
     * @param template
     * @param parse
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class, SQLException.class})
    public int  insertFormTemplent(  String template, String parse, String id, String tableName )
    {

        return formListMapper.insertFormTemplent( template, parse, id, tableName );
    }

    @Transactional(readOnly = true)
    public Map<String, Object> queryFormData( String formId, String dataId, String userId ){
        String tableName = formListMapper.queryTableName(formId);
        return formListMapper.queryTableData(tableName, dataId);
    }


}
