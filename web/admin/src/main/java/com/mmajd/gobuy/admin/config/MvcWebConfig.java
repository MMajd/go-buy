package com.mmajd.gobuy.admin.config;

import com.mmajd.gobuy.common.constant.ASSETS_CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
@Slf4j
public class MvcWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // NOTE: Path is prefixed with / in the beginning
        String assetsAbsolutePath = Path
                .of(ASSETS_CONSTANTS.ASSET_DIR.getValue())
                .toFile().getAbsolutePath();

        registry.addResourceHandler(ASSETS_CONSTANTS.ASSET_DIR.getValue() + "/**")
                // NOTE: Final will be like file:///location
                .addResourceLocations("file://" + assetsAbsolutePath + "/");
    }

}
