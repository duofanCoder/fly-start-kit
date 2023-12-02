package com.duofan.fly.manage.api.controller.api.v1.nav;


import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.storage.FlyDictDataService;
import com.duofan.fly.manage.api.controller.request.FlyDictDataRequest;
import org.springframework.web.bind.annotation.*;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import java.util.Arrays;

/**
* <p>
*  前端控制器
* </p>
*
* @author duofan
* @since 2023-12-03
*/
@RestController
@RequestMapping("/api/v1/flyDictData")
@FlyAccessInfo(opName = "FlyDictData模块")
public class FlyDictDataController {
    @Resource
    private FlyDictDataService service;

    @GetMapping("/page")
    @FlyAccessInfo(opName = "FlyDictData-分页")
    public FlyResult page(FlyPageInfo<FlyDictData> pageInfo, FlyDictData entity){
        return FlyResult.success(service.page(pageInfo,entity));
    }

    @GetMapping("/detail")
    @FlyAccessInfo(opName = "FlyDictData-详细信息")
    public FlyResult detail(String id){
        return FlyResult.success(service.getById(id));
    }

    @DeleteMapping("/remove")
    @FlyAccessInfo(opName = "FlyDictData-删除信息")
    public FlyResult remove(String id){
        return FlyResult.success(service.removeById(id));
    }

    @PutMapping("/update")
    @FlyAccessInfo(opName = "FlyDictData-修改信息")
    public FlyResult updateById(@RequestBody @Valid FlyDictData request){
        service.updateById(request);
        return FlyResult.SUCCESS;
    }

    @PostMapping("/save")
    @FlyAccessInfo(opName = "FlyDictData-添加信息")
    public FlyResult save(@RequestBody @Valid FlyDictData request){
        return FlyResult.success(service.save(request));
    }
    @DeleteMapping("/remove/batch")
    @FlyAccessInfo(opName = "FlyDictData-批量删除")
    public FlyResult removeByIds(@RequestParam("ids") String[] ids) {
        service.removeByIds(Arrays.asList(ids));
        return FlyResult.SUCCESS;
    }

    @PutMapping("/switch/status")
    @FlyAccessInfo(opName = "FlyDictData-切换状态")
    public FlyResult switchVisible(@RequestBody @Valid FlyDictDataRequest.SwitchStatus request) {
        return FlyResult.success(service.switchStatus(request.getId(), request.getStatus()));
    }
}
