package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 起飞 日志信息类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/13
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
public class FlyLog extends EntityBase {
    // 模块名称 - 中文名
    private String moduleName;
    // 模块包路径
    private String module;
    // 方法名称
    private String op;
    // 方法名称 - 中文名
    private String opName;
    // 描述
    private String description;
    // 访问结束时间
    private Date accessStartTime;
    // 访问结束时间
    private Date accessEndTime;
    // 访问耗时 单位ms 毫秒
    private Long accessUseTime;
    private String ipv4;
    private String ipv6;
}
