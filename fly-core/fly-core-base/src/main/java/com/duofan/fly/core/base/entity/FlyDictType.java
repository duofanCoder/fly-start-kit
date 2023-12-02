package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
public class FlyDictType extends BaseEntity {

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    @Column(columnDefinition = "varchar(255) NOT NULL DEFAULT '1'")
    private String isEnabled;

}
