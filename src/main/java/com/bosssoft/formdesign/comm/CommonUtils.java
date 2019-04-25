package com.bosssoft.formdesign.comm;

import com.bosssoft.formdesign.page.DataTablePageUtil;
import com.bosssoft.formdesign.page.PageInfo;

import java.util.List;
import java.util.UUID;

/**
  * @类描述： 通用工具类
  * @类名称： CommonUtils
  * @创建人： fw
  * @创建时间： 2018-12-25
  */

public class CommonUtils {


    /**
     * 获取32 UUID位主键
     * @return
     */
    public static String getUUID32(){

        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;

    }

    @SuppressWarnings("unchecked")
    public static <T> DataTablePageUtil getReturnDataTable(DataTablePageUtil dataTable, List<T> dataList) {
        PageInfo<T> pageInfo = new PageInfo(dataList);
        dataTable.setDraw(dataTable.getDraw());
        dataTable.setData(pageInfo.getList());
        dataTable.setRecordsTotal((int) pageInfo.getTotal());
        dataTable.setRecordsFiltered(dataTable.getRecordsTotal());
        return dataTable;
    }

}
