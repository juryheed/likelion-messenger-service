package org.mjulikelion.likelionmessengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupDto {
    //회원 가입에는 이름, 근무자 아이디, 비밀번호가 필요하다

    //name
    @NotBlank(message = "이름이 누락되었습니다")
    @Size(min = 1,max = 20,message = "이름은 최소 한글자 최대 20글자 입니다")
    private String name;

    //companyId
    @NotBlank(message = "근무자 아이디가 누락되었습니다")
    @Size(min=5,max=20,message = "아이디는 최소 5글자 최대 20글자 입니다")
    private String companyId;

    //password
    @NotBlank(message = "비밀번호가 누락되었습니다")
    @Size(message = "비밀번호는 5글자 이상, 20글자 이하입니다.", min = 5, max = 20)
    private String password;
}
