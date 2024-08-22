package com.yuki.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
     
    @Value("${file-save-path}")
    private String fileSavePath;

    @Value("${kaiwa-path}")
    private String kaiwaPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+fileSavePath);
        registry.addResourceHandler("/audio/**").addResourceLocations("file:"+kaiwaPath);
        //System.out.println("file:"+fileSavePath);
    }
 
}