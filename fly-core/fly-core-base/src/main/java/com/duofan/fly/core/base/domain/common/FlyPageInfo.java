package com.duofan.fly.core.base.domain.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Data
@Accessors(chain = true)
public class FlyPageInfo implements Serializable {
    private long pageSize = 20;
    private long pageIndex = 1;
    private long pageCount = 0;
    private List<T> list;
    /**
     * 排序 columnName desc,columnName2 asc
     */
    private String order = "updateTime desc";

    public static FlyPageInfo to(Page<T> page) {
        FlyPageInfo result = new FlyPageInfo();
        result.pageCount = page.getPages();
        result.pageSize = page.getSize();
        result.list = page.getRecords();
        result.setPageIndex(page.getCurrent());
        return result;
    }
}
