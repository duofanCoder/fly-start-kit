package com.duofan.fly.api.file.object.VO;

import com.duofan.fly.core.base.entity.FlyFileMetaData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.io.Resource;

/**
 * 资源相关
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ResourceVO {
    private Resource resource;

    private FlyFileMetaData metaData;
}
