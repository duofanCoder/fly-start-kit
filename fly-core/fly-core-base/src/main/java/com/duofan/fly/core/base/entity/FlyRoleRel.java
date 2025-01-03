package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 角色关系
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
@AllArgsConstructor
public class FlyRoleRel extends BaseEntity {
    private String username;
    private String roleNo;
    private String rel;

    public FlyRoleRel(String username, String roleNo) {
        this.username = username;
        this.roleNo = roleNo;
    }

    public FlyRoleRel() {

    }
}
