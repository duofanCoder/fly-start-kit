package com.duofan.fly.manage.api.controller.api.v1.nav;


import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDictType;
import com.duofan.fly.core.storage.FlyDictTypeService;
import com.duofan.fly.manage.api.controller.request.FlyDictTypeRequest;
import org.springframework.web.bind.annotation.*;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duofan
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/api/v1/flyDictType")
@FlyAccessInfo(opName = "FlyDictType模块")
public class FlyDictTypeController {
    @Resource
    private FlyDictTypeService service;

    @GetMapping("/page")
    @FlyAccessInfo(opName = "FlyDictType-分页")
    public FlyResult page(FlyPageInfo<FlyDictType> pageInfo, FlyDictType entity) {
        return FlyResult.success(service.page(pageInfo, entity));
    }

    @GetMapping("/detail")
    @FlyAccessInfo(opName = "FlyDictType-详细信息")
    public FlyResult detail(String id) {
        return FlyResult.success(service.getById(id));
    }

    @DeleteMapping("/remove")
    @FlyAccessInfo(opName = "FlyDictType-删除信息")
    public FlyResult remove(String id) {
        return FlyResult.success(service.removeById(id));
    }

    @PutMapping("/update")
    @FlyAccessInfo(opName = "FlyDictType-修改信息")
    public FlyResult updateById(@RequestBody @Valid FlyDictType request) {
        service.updateById(request);
        return FlyResult.SUCCESS;
    }

    @PostMapping("/save")
    @FlyAccessInfo(opName = "FlyDictType-添加信息")
    public FlyResult save(@RequestBody @Valid FlyDictType request) {
        return FlyResult.success(service.save(request));
    }

    @DeleteMapping("/remove/batch")
    @FlyAccessInfo(opName = "FlyDictType-批量删除")
    public FlyResult removeByIds(@RequestParam("ids") String[] ids) {
        service.removeByIds(Arrays.asList(ids));
        return FlyResult.SUCCESS;
    }

    @PutMapping("/switch/status")
    @FlyAccessInfo(opName = "FlyDictType-切换状态")
    public FlyResult switchVisible(@RequestBody @Valid FlyDictTypeRequest.SwitchStatus request) {
        return FlyResult.success(service.switchStatus(request.getId(), request.getStatus()));
    }
}
