package skhu.hanziboong.global.exception.model;

import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
