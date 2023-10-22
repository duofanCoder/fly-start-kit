package com.duofan.fly.core.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Entity
@Table
@RequiredArgsConstructor
@Accessors(chain = true)
public class FlyUser extends BaseEntity {
    private String username;
    @JsonIgnore
    @JsonDeserialize
    private String password;
    private String gender;
    private String birth;
    private String idCardNo;
    private String email;
    private String phone;
    private String avatar;
    @Column(columnDefinition = "varchar(1) default 1")
    private String isLocked;
    @Column(columnDefinition = "varchar(1) default 1")
    private String isEnabled;

    @Transient
    @TableField(exist = false)
    private String roleNo;
}
