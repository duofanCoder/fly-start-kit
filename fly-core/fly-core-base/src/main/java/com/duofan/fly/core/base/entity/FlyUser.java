package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Entity
@Table
@RequiredArgsConstructor
@Accessors(chain = true)
public class FlyUser extends EntityBase {
    private String username;
    private String password;
    private String gender;
    private String age;
    private String birth;
    private String idCardNo;
    private String email;
    private String phone;
    private String avatarImg;
}
