/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.duofan.fly.manage.generator.config.builder;


import com.duofan.fly.manage.generator.config.IConfigBuilder;
import com.duofan.fly.manage.generator.config.StrategyConfig;

/**
 * 配置构建
 *
 * @author nieqiurong 2020/10/11.
 * @since 3.5.0
 */
public class BaseBuilder implements IConfigBuilder<StrategyConfig> {

    private final StrategyConfig strategyConfig;

    public BaseBuilder(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }


    public Entity.Builder entityBuilder() {
        return strategyConfig.entityBuilder();
    }


    public Controller.Builder controllerBuilder() {
        return strategyConfig.controllerBuilder();
    }


    public Mapper.Builder mapperBuilder() {
        return strategyConfig.mapperBuilder();
    }


    public Service.Builder serviceBuilder() {
        return strategyConfig.serviceBuilder();
    }


    @Override
    public StrategyConfig build() {
        this.strategyConfig.validate();
        return this.strategyConfig;
    }
}
