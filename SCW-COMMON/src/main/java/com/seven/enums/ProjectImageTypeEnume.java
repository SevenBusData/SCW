package com.seven.enums;

/**
 * @author: seven
 * @since: 2024/7/11 13:20
 */
public enum ProjectImageTypeEnume {
    HEAD(0, "预览图"),
    DETAIL(1, "详细图");

    private ProjectImageTypeEnume(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


