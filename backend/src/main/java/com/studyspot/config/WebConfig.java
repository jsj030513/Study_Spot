package com.studyspot.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${study-spot.cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MediaType utf8Json = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConverter) {
                jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);

                List<MediaType> mediaTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
                mediaTypes.removeIf(mediaType -> MediaType.APPLICATION_JSON.includes(mediaType));
                mediaTypes.add(0, utf8Json);
                jacksonConverter.setSupportedMediaTypes(mediaTypes);
            }
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**")
                .addResourceLocations("file:../html/", "file:html/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("file:../css/", "file:css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("file:../js/", "file:js/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/html/index.html");
        registry.addViewController("/main").setViewName("forward:/html/main.html");
        registry.addViewController("/login").setViewName("forward:/html/login.html");
        registry.addViewController("/join").setViewName("forward:/html/join.html");
        registry.addViewController("/mypage").setViewName("forward:/html/mypage.html");
        registry.addViewController("/admin").setViewName("forward:/html/admin.html");
    }
}
