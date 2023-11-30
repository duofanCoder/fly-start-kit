<#assign entityUnderline = entity?replace("[A-Z]", "_$0", "r")?upper_case?substring(1)>
<#assign entityLowFirst = entity?uncap_first>
package ${package.Controller};

<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>

import org.springframework.web.bind.annotation.*;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping("/api/v1/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@FlyAccessInfo(opName = "${entity}模块")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>
    @Resource
    private ${table.serviceName} service;

    @GetMapping("/page")
    @FlyAccessInfo(opName = "${entity}-分页")
    public FlyResult page(FlyPageInfo<${entity}> pageInfo,${entity} entity){
        return FlyResult.success(service.page(pageInfo,entity));
    }

    @GetMapping("/detail")
    @FlyAccessInfo(opName = "${entity}-详细信息")
    public FlyResult detail(String id){
        return FlyResult.success(service.getById(id));
    }

    @DeleteMapping("/remove")
    @FlyAccessInfo(opName = "${entity}-删除信息")
    public FlyResult remove(String id){
        return FlyResult.success(service.removeById(id));
    }

    @PutMapping("/update")
    @FlyAccessInfo(opName = "${entity}-修改信息")
    public FlyResult updateById(@RequestBody @Valid ${entity} request){
        service.updateById(request);
        return FlyResult.SUCCESS;
    }

    @PostMapping("/save")
    @FlyAccessInfo(opName = "${entity}-添加信息")
    public FlyResult save(@RequestBody @Valid ${entity} request){
        return FlyResult.success(service.save(request));
    }
    @DeleteMapping("/remove/batch")
    @FlyAccessInfo(opName = "${entity}-批量删除")
    public FlyResult removeByIds(@RequestParam("ids") String[] ids) {
        service.removeByIds(Arrays.asList(ids));
        return FlyResult.SUCCESS;
    }

    @PutMapping("/switch/status")
    @FlyAccessInfo(opName = "${entity}-切换状态")
    public FlyResult switchVisible(@RequestBody @Valid ${entity}Request.SwitchStatus request) {
        return FlyResult.success(service.switchStatus(request.getId(), request.getStatus()));
    }
}
</#if>
