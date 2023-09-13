package com.duofan.fly.core.base.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页信息
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Data
public class PageInfo implements Serializable {
    /**
     * 分页大小
     */
    private long pageSize = 20;

    /**
     * 当前页
     */
    private long pageIndex = 1;

    /**
     * 排序 columnName desc,columnName2 asc
     */
    private String order = "updateTime desc";
}
