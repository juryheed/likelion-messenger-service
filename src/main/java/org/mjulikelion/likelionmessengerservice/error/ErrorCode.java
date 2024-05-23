package org.mjulikelion.likelionmessengerservice.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //인증
    PASSWORD_NOT_EQUAL("4010", "직원번호와 패스워드가 일치하지 않습니다"),

    //인가(접근)
    CANT_ACCESS("4030", "접근이 불가합니다"),
    TOKEN_NOT_FOUND("4031", "토큰이 없습니다"),
    TOKEN_INVALID("4032", "토큰이 유효하지 않습니다"),

    //리소스 찾을 수 없음
    EMPLOIEE_NOT_FOUND("4040", "직원를 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND("4041", "메신저를 찾을 수 없습니다."),
    CONTENT_NOT_FOUND("4042", "메신저 내용을 찾을 수 없습니다"),
    RECIEVER_NOT_FOUND("4043","수신자를 찾을수 없습니다"),

    //리소스 중복
    EMPLOIEE_DUPLICATION("4090", "이미 가입된 직원 입니다"),

    NOT_NULL("9001", "필수값이 누락되었습니다."),
    NOT_BLANK("9002", "필수값이 빈 값이거나 공백으로 되어있습니다."),
    REGEX("9003", "형식에 맞지 않습니다."),
    LENGTH("9004", "길이가 유효하지 않습니다.");


    private final String code;
    private final String message;

    //Dto의 어노테이션을 통해 발생한 에러코드를 반환
    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Pattern" -> REGEX;
            case "Length" -> LENGTH;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
