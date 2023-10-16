package com.duofan.fly.manage.api.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyDict;
import com.duofan.fly.core.storage.FlyDictStorage;
import com.duofan.fly.manage.api.controller.request.DictRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/dict")
@FlyAccessInfo(moduleName = "字典管理", system = "FLY BOOT")
public class FlyDictController {

    @Resource
    private FlyDictStorage dictStorage;

    @PostMapping("/page")
    @FlyAccessInfo(opName = "分页")
    FlyResult page(@RequestBody(required = false) FlyPageInfo<FlyDict> pageInfo, @RequestBody(required = false) FlyDict dict) {
        return FlyResult.success(dictStorage.page(pageInfo, dict));
    }

    @PostMapping("/batchList")
    @FlyAccessInfo(opName = "查询")
    FlyResult batchList(
            @RequestBody @NotNull @NotEmpty
            List<String> typeList) {
        List<FlyDict> ret = dictStorage.list(typeList);
        return FlyResult.success(ret);
    }

    @PostMapping("/create")
    @FlyAccessInfo(opName = "创建")
    FlyResult create(@RequestBody @Valid DictRequest.Save request) {
        dictStorage.save(BeanUtil.copyProperties(request, FlyDict.class));
        return FlyResult.SUCCESS;
    }

    @PostMapping("/update")
    @FlyAccessInfo(opName = "修改")
    FlyResult update(@RequestBody @Valid DictRequest.Update request) {
        dictStorage.update(BeanUtil.copyProperties(request, FlyDict.class));
        return FlyResult.SUCCESS;
    }
}
