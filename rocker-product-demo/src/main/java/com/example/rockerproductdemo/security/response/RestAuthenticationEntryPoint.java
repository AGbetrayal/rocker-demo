package com.example.rockerproductdemo.security.response;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 *
 * @author AGbetrayal
 * @date 2020/5/30 15:17
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
//        response.getWriter().println(JSONUtil.parse("当未登录或者token失效访问接口时，自定义的返回结果"));
        response.getWriter().println("当未登录或者token失效访问接口时，自定义的返回结果");
        response.getWriter().flush();
    }
}
