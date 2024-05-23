package org.mjulikelion.likelionmessengerservice.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RePasswordDto {
    //비밀번호 수정에는 현재 비밀번호와 변경 할 비밀번호가 필요하다

    //현재 비밀번호
    @NotBlank(message = "현재 비밀번호가 누락되었습니다")
    @Size(message = "비밀번호는 5글자 이상, 20글자 이하입니다.", min = 5, max = 20)
    private String nowPassword;

    //새 비밀번호
    @NotBlank(message = "변경할 비밀번호가 누락되었습니다")
    @Size(message = "비밀번호는 5글자 이상, 20글자 이하입니다.", min = 5, max = 20)
    private String newPassword;
}
