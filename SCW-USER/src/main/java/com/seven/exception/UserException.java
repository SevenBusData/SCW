package com.seven.exception;

import com.seven.enums.UserExceptionEnum;

/**
 * @author: seven
 * @since: 2024/7/11 10:49
 */

public class UserException extends RuntimeException {

    public UserException(UserExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
    }
}