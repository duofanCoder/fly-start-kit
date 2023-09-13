package com.duofan.fly.core.base.entity.abstact;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class EntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @TableId
    private String id;
    @JSONField(deserialize = false)
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(deserialize = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @JSONField(deserialize = false)
    private String createBy;
    @JSONField(deserialize = false)
    private String updateBy;
    @JSONField(deserialize = false, serialize = false)
    @Column(columnDefinition = "varchar(32) default 0")
    private String isDeleted;
    private String remark;
    private String sort;
}
