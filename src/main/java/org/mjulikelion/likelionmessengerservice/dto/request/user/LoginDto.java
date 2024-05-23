package org.mjulikelion.likelionmessengerservice.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginDto {
    //로그인은 companyId와 password로 한다

    //companyId
    @NotBlank(message = "근무자 아이디가 누락되었습니다")
    @Size(min=5,max=20,message = "아이디는 최소 5글자 최대 20글자 입니다")
    private String companyId;

    //password
    @NotBlank(message = "비밀번호가 누락되었습니다")
    @Size(message = "비밀번호는 5글자 이상, 20글자 이하입니다.", min = 5, max = 20)
    private String password;
}
