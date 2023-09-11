package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.EntityBase;
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
 * @date 2023/9/11
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
public class FlyDic extends EntityBase {
    private String dicType;
    private String text;
    private String code;
}
