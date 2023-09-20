package com.duofan.fly.core.base.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Getter
@Setter
@ToString
@Entity
@Table
@RequiredArgsConstructor
@Accessors(chain = true)
public class FlyUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @TableId
    private String username;
    @JSONField(serialize = false)
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
