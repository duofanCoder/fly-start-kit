package com.duofan.fly.core.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

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

    @Transient
    @TableField(exist = false)
    private List<FlyDict> list;
}
