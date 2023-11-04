package com.duofan.fly.core.utils;

import cn.hutool.json.JSONUtil;
import com.duofan.fly.core.base.domain.common.FlyResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * web工具集合
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
public class WebUtils {


    public static void responseJson(HttpServletResponse response, FlyResult result) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(result));
        response.getWriter().flush();
    }
}
