package com.duofan.fly.api.file.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.mapper.FlyFileMetaDataMapper;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文件元信息数据库操作接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */
@Slf4j
@Service
public class FlyDefaultFileMetaDataStorage extends ServiceImpl<FlyFileMetaDataMapper, FlyFileMetaData> implements FlyFileMetaDataStorage {


}
