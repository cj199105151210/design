package com.bosssoft.formdesign.service;

import com.bosssoft.formdesign.comm.CommonUtils;
import com.bosssoft.formdesign.domain.ZTreeVo;
import com.bosssoft.formdesign.formMapper.FormTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
  * @类描述： 表单操作
  * @类名称： FormTypeService
  * @创建人： fw
  * @创建时间： 2018-12-24
  */
@Service
@Transactional
@Slf4j
public class FormTypeService {

    @Autowired
    private FormTypeMapper formTypeMapper;

    public List<ZTreeVo> selectAllByType()
    {
        return formTypeMapper.selectAllByType();
    }

    public int deleteTypeById(String guid)
    {
        int num = formTypeMapper.deleteById(guid);
        return num;
    }

    public int saveByTree( ZTreeVo zv  )
    {
        zv.setId(CommonUtils.getUUID32());
        int num = formTypeMapper.saveByTree(zv);
        return num;
    }
    public  int updateByTree( ZTreeVo zv )
    {
        int num = formTypeMapper.updateByTree(zv);
        return num;
    }

    public String  selectName(String id)
    {
        return formTypeMapper.selectName(id);
     }

    public Boolean checkFormName(String formName) {
        if (formTypeMapper.checkFormName(formName) > 0) {
            return true;
        } else {
            return false;
        }
    }



}
