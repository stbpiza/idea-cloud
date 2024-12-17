package org.ideacloud.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "기간이 만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "로그인 실패"),
    TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "기타 토큰 에러"),
    DUPLICATE_DB_ENTRY(HttpStatus.CONFLICT, "DB 중복 에러"),
    EMAIL_VERIFICATION_EXPIRED(HttpStatus.BAD_REQUEST, "이메일 인증 유효시간 만료"),
    EMAIL_ALREADY_USED(HttpStatus.CONFLICT, "이미 사용중인 이메일"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 DB 에러"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 값 요청"),
    CONFLICT(HttpStatus.CONFLICT, "409"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없음"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401"),
    ;


    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message){
        this.status = status;
        this.message = message;
    }
}
