package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.EntityBase;
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
 * @date 2023/9/11
 */
@Getter
@Setter
@ToString
@Entity
@Table
@RequiredArgsConstructor
@Accessors(chain = true)
public class FlyRole extends EntityBase {
    private String roleName;
    private String roleNo;
    @Column(columnDefinition = "varchar(32) default 1")
    private String isEnabled;
}
