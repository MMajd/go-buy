package com.mmajd.gobuy.admin.config;

import com.mmajd.gobuy.admin.contant.ASSETS_CONSTANTS;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebAssetsConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String assetsAbsolutePath = Path
                .of(ASSETS_CONSTANTS.ASSET_DIR.getValue())
                .toFile().getAbsolutePath();

        registry.addResourceHandler("/" + ASSETS_CONSTANTS.ASSET_DIR.getValue() + "/**")
                .addResourceLocations("file:/" + assetsAbsolutePath + "/");
    }

}
