package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Setter
@Getter
@Entity
@Table(indexes = {@Index(name = "dict_index", columnList = "type")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"type", "code"})
)
@Accessors(chain = true)
public class FlyDict extends BaseEntity {
    private String type;
    private String text;
    private String code;

    // 是否启用
    private String isEnabled;
    // 字典名称
    private String name;
    // 字典值的描述
    private String description;

    public FlyDict(String type, String text, String code) {
        this.type = type;
        this.text = text;
        this.code = code;
    }

    public FlyDict() {

    }
}
