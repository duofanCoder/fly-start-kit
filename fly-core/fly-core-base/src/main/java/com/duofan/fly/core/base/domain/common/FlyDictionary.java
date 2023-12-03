package com.duofan.fly.core.base.domain.common;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/12/3
 */
@Data
public class FlyDictionary implements Serializable {
    private String id;
    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格字典样式
     */
    private String listClass;

    /**
     * 是否默认（1是 0否）
     */
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private String isEnabled;

    public FlyDictionary(String type, String label, String value) {
        this.type = type;
        this.label = label;
        this.value = value;
    }
}
