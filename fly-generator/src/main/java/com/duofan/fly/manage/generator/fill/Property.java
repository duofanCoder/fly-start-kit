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
package com.duofan.fly.manage.generator.fill;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.duofan.fly.manage.generator.IFill;


/**
 * 属性填充
 *
 * @author nieqiurong
 * @since 3.5.0 2020/11/30.
 */
public class Property implements IFill {

    private final String propertyName;

    private final FieldFill fieldFill;

    public Property(String propertyName, FieldFill fieldFill) {
        this.propertyName = propertyName;
        this.fieldFill = fieldFill;
    }

    public Property(String propertyName) {
        this.propertyName = propertyName;
        this.fieldFill = FieldFill.DEFAULT;
    }

    @Override
    public String getName() {
        return this.propertyName;
    }

    @Override
    public FieldFill getFieldFill() {
        return this.fieldFill;
    }
}
