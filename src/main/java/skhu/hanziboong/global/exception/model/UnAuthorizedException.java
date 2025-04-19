package skhu.hanziboong.global.exception.model;

import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
