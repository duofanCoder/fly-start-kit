package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    boolean save(${entity} entity);

    boolean edit(${entity} entity);

    FlyPageInfo<${entity}> page(FlyPageInfo<${entity}> pageInfo,${entity} entity);

    boolean switchStatus(String id, String status);
}
</#if>
