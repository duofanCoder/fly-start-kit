package com.duofan.fly.framework.mybatis.aspect;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.duofan.fly.framework.security.context.FlySessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "createTime", Date.class, new Date());
        strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        strictInsertFill(metaObject, "createBy", String.class, getUsername());
        strictInsertFill(metaObject, "updateBy", String.class, getUsername());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        strictInsertFill(metaObject, "updateBy", String.class, getUsername());
    }


    private String getUsername() {
        try {
            return FlySessionHolder.currentUser().getUsername();
        } catch (Exception e) {
            return "anonymous";
        }
    }
}
