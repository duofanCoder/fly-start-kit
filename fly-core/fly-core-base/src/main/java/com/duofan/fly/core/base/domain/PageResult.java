package com.duofan.fly.core.base.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
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
public class PageResult implements Serializable {
    private long pageSize = 20;
    private long pageIndex = 1;
    private long pageCount = 0;
    private List<T> list;

    public static PageResult to(Page<T> page) {
        PageResult result = new PageResult();
        result.pageCount = page.getPages();
        result.pageSize = page.getSize();
        result.list = page.getRecords();
        result.setPageIndex(page.getCurrent());
        return result;
    }
}
