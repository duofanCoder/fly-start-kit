package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

public class FlyRoleRel extends EntityBase {
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
