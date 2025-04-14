package skhu.hanziboong.global.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import skhu.hanziboong.global.exception.ErrorCode;
import skhu.hanziboong.global.exception.SuccessCode;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ApiResponse<T> {

    private final int status;
    private final boolean success;
    private final String message;
    private T data;

    public static <T> ResponseEntity<ApiResponse<T>> success(SuccessCode successCode, T data) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .status(successCode.getHttpStatusCode())
                .success(true)
                .message(successCode.getMessage())
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode, T data) {
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ApiResponse.<T>builder()
                        .status(errorCode.getHttpStatusCode())
                        .success(false)
                        .message(errorCode.getMessage())
                        .data(data)
                        .build());
    }
}
