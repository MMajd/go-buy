package com.mmajd.gobuy.admin.config;

import com.mmajd.gobuy.common.constant.ASSETS_CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.util.function.BiFunction;

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

    @Bean
    public BiFunction<String, Object, String> replaceOrAddParam() {
        return (paramName, newValue) -> ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam(paramName, newValue)
                .toUriString();
    }

}
