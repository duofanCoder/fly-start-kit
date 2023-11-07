package com.duofan.fly.web.starter.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
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
 * @date 2023/11/7
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
public class FlyStudent extends BaseEntity {

    private String name;
}
