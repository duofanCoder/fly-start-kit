package com.duofan.fly.core.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 角色操作权限
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Setter
@Getter
@Entity
@Table(uniqueConstraints = {})
@Accessors(chain = true)
public class FlyRolePermission extends BaseEntity {
    private String roleNo;
    private String module;
    private String op;
    @Transient
    @TableField(exist = false)
    private boolean isActivated;
}
