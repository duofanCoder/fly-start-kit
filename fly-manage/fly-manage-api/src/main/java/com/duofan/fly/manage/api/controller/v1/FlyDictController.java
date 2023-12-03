package com.duofan.fly.manage.api.controller.v1;

import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyDictType;
import com.duofan.fly.core.storage.FlyDictTypeStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private FlyDictTypeStorage dictStorage;

//    @GetMapping("/page")
//    @FlyAccessInfo(opName = "分页")
//    FlyResult page(FlyPageInfo<FlyDict> pageInfo, FlyDict dict) {
//        return FlyResult.success(dictStorage.page(pageInfo, dict));
//    }

    /**
     * 查询字典，支持多字典查询用逗号分割
     * @param type
     * @return
     */
    @GetMapping("/list")
    @FlyAccessInfo(opName = "字典查询",isDebounce = false)
    FlyResult batchList(@RequestParam String type) {
        return FlyResult.success(dictStorage.listWrap(type));
    }

//    @DeleteMapping("/remove")
//    @FlyAccessInfo(opName = "删除字典值")
//    FlyResult remove(@RequestParam String id) {
//        return FlyResult.success(dictStorage.removeById(id));
//    }
//
//    @PostMapping("/add")
//    @FlyAccessInfo(opName = "创建")
//    FlyResult create(@RequestBody @Valid DictRequest.Save request) {
//        dictStorage.save(BeanUtil.copyProperties(request, FlyDict.class));
//        return FlyResult.SUCCESS;
//    }
//
//    @PutMapping("/update")
//    @FlyAccessInfo(opName = "修改")
//    FlyResult update(@RequestBody @Valid DictRequest.Update request) {
//        dictStorage.update(BeanUtil.copyProperties(request, FlyDict.class));
//        return FlyResult.SUCCESS;
//    }
//
//    @PutMapping("/enabled")
//    @FlyAccessInfo(opName = "切换启用")
//    FlyResult enabled(@RequestBody @Valid DictRequest.Enabled request) {
//        dictStorage.switchEnabled(BeanUtil.copyProperties(request, FlyDict.class));
//        return FlyResult.SUCCESS;
//    }
}
