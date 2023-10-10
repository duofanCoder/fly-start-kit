package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @JsonDeserialize
    private String password;
    private String gender;
    private String age;
    private String birth;
    private String idCardNo;
    private String email;
    private String phone;
    private String avatarImg;
    @Column(columnDefinition = "varchar(1) default 1")
    private String isLocked = "0";
    @Column(columnDefinition = "varchar(1) default 1")
    private String isEnabled = "1";


}
