package org.example.commodity_management_system_jzs.config;

import org.example.commodity_management_system_jzs.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 * - 注册登录拦截器
 * - 配置文件上传路径的静态资源映射（使上传的图片可访问）
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-path}")
    private String uploadPath;

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")                        // 拦截所有请求
                .excludePathPatterns(                          // 放行以下路径
                        "/login",                              // 登录页面
                        "/doLogin",                            // 登录请求
                        "/css/**",                             // 静态样式
                        "/js/**",                              // 静态脚本
                        "/photo/**",                           // 静态图片
                        "/uploads/**"                          // 上传的图片
                );
    }

    /**
     * 配置文件上传路径 → URL 映射
     * 访问 http://localhost:8080/uploads/xxx.jpg → 读取 D:/spring-boot/期末设计/uploads/xxx.jpg
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath);
    }
}
