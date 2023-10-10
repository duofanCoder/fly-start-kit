package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(indexes = {@Index(name = "role_no_index", columnList = "roleNo", unique = true)},
        uniqueConstraints = @UniqueConstraint(columnNames = {"roleNo"})
)
@RequiredArgsConstructor
@Accessors(chain = true)
public class FlyRole extends BaseEntity {
    private String roleName;
    private String roleNo;
    @JsonIgnore
    @Column(columnDefinition = "varchar(32) default 1")
    private String isEnabled;
}
