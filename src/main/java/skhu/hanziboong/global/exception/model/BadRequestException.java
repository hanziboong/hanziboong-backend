package skhu.hanziboong.global.exception.model;

import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
