package com.mmajd.gobuy.common.constant;

public enum ASSETS_CONSTANTS {
    ASSET_DIR("assets"),
    IMAGES_DIR("assets/images"),
    USER_IMAGES_DIR("assets/images/user");


    private final String value;

    private ASSETS_CONSTANTS(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public String getDir() {
        return "/" + value;
    }
}
