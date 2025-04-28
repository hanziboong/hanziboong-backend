package skhu.hanziboong.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK
    LOGIN_USER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다"),
    SIGNUP_USER_SUCCESS(HttpStatus.OK, "회원가입에 성공했습니다"),
    SMS_CERT_MESSAGE_SUCCESS(HttpStatus.OK, "메세지 전송이 완료되었습니다."),
    SMS_VERIFY_SUCCESS(HttpStatus.OK, "본인확인 인증에 성공했습니다");

    // 201 Created, Delete
    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
