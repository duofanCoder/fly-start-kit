package com.duofan.fly.core.base.domain;

import com.duofan.fly.core.base.enums.FlyHttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FlyResult implements Serializable {
    private String code;
    private String msg;
    private T data;


    public static FlyResult SUCCESS = of(FlyHttpStatus.SUCCESS);
    public static FlyResult FAIL = of(FlyHttpStatus.FAIL);


    public static FlyResult of(FlyHttpStatus status) {
        return new FlyResult()
                .setCode(status.getCode())
                .setMsg(status.getMsg())
                .setData(null);
    }
}
