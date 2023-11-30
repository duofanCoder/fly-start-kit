package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    @Resource
    private ${table.mapperName} mapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean save(${entity} entity){
        entity.setId(null);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean edit(${entity} entity){
        return updateById(entity);
    }

    @Override
    public FlyPageInfo<${entity}>page(FlyPageInfo<${entity}>pageInfo,${entity} user){
        Page<${entity}>page=QueryUtils.buildPage(pageInfo,${entity}.class);
        QueryWrapper<${entity}>wp=QueryUtils.buildQueryWrapper(user,List.of("createTime"),${entity}.class);
        Page<${entity}>data=page(page,wp);
        wp.orderByDesc("update_time");
        return FlyPageInfo.of(data);
    }


    @Override
    public boolean switchStatus(String id, String status) {
        ${entity} model = new ${entity}();
        model
        //.setStatus(status)
        .setId(id);
        return this.updateById(model);
    }
}
</#if>
