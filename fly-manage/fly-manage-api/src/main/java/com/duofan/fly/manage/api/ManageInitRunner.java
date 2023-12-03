package com.duofan.fly.manage.api;

import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.base.entity.*;
import com.duofan.fly.core.storage.FlyDictDataStorage;
import com.duofan.fly.core.storage.FlyDictTypeStorage;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.manage.api.service.impl.FlyDefaultUserStorage;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
@Component
public class ManageInitRunner implements CommandLineRunner {
    @Resource
    private FlyDefaultUserStorage userStorage;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private FlyDictTypeStorage dictTypeStorage;

    @Resource
    private FlyDictDataStorage dictDataStorage;

    @Resource
    private FlyRoleStorage roleStorage;

    @Override
    public void run(String... args) throws Exception {
        dictInit();

        if (!isInit()) {
            return;
        }
        userInit();
        roleInit();
        dictInit();
        roleRelInit();
    }

    /**
     * 系统字典初始化
     */
    private void dictInit() {
        List<FlyDictionary> booleanDict = dictTypeStorage.getOne("booleanDict");
        if (booleanDict == null) {
            // 先插入type ，再插入data
            ;
            dictTypeStorage.save(new FlyDictType()
                    .setIsEnabled("1")
                    .setType("booleanDict")
                    .setName("布尔字典"));
            dictDataStorage.save(new FlyDictData()
                    .setType("booleanDict")
                    .setIsEnabled("1")
                    .setValue("0")
                    .setLabel("否"));
            dictDataStorage.save(new FlyDictData()
                    .setType("booleanDict")
                    .setIsEnabled("1")
                    .setValue("1")
                    .setLabel("是"));
        }

    }

    private boolean isInit() {
        FlyUser admin = userStorage.getByUsername("admin");
        if (admin != null) {
            return false;
        }
        return true;
    }

    private void userInit() {

        FlyUser adminEntity = new FlyUser()
                .setUsername("admin")
                .setPassword(passwordEncoder.encode("123456"))
                .setEmail("duofancc@qq.com")
                .setBirth("1998-10-22")
                .setGender("1")
                .setPhone("1515179xxxx")
                .setIdCardNo("320724199810225478")
                .setIsLocked("0")
                .setIsEnabled("1");

        FlyUser commonEntity = new FlyUser()
                .setUsername("common")
                .setPassword(passwordEncoder.encode("123456"))
                .setEmail("common@qq.com")
                .setBirth("1991-12-22")
                .setGender("0")
                .setPhone("1533179xxxx")
                .setIdCardNo("312724199810225478")
                .setIsLocked("0")
                .setIsEnabled("1");

        userStorage.save(adminEntity);
        userStorage.save(commonEntity);

    }

    private void roleInit() {
        if (roleStorage.count() != 0) {
            return;
        }

        FlyRole role = new FlyRole()
                .setIsEnabled("1")
                .setRoleNo("ADMIN")
                .setRoleName("管理员");
        FlyRole role2 = new FlyRole()
                .setIsEnabled("1")
                .setRoleNo("COMMON")
                .setRoleName("普通用户");
        roleStorage.save(role);
        roleStorage.save(role2);

    }

    private void roleRelInit() {
        FlyRoleRel rel = new FlyRoleRel();
        rel.setRoleNo("ADMIN")
                .setUsername("admin");
        roleStorage.addRoleRel(rel);
        FlyRoleRel rel2 = new FlyRoleRel();
        rel2.setRoleNo("COMMON")
                .setUsername("common");
        roleStorage.addRoleRel(rel);
        roleStorage.addRoleRel(rel2);
    }

}
