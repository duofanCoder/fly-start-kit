package com.duofan.fly.core.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/10
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
public class FlyMenu extends BaseEntity {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private String isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String isHidden;

    /**
     * 权限字符串
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    @Transient
    @TableField(exist = false)
    private List<FlyMenu> children = new ArrayList<FlyMenu>();
}
