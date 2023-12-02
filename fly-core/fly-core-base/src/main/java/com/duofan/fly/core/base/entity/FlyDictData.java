package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/1/7
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
public class FlyDictData extends BaseEntity {

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
    @Column(name = "is_default", columnDefinition = "tinyint NOT NULL DEFAULT '1'")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @Column(columnDefinition = "varchar(255) NOT NULL DEFAULT '1'", nullable = false)
    private String isEnabled;
}
