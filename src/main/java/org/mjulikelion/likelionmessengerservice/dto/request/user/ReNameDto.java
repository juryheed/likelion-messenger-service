package org.mjulikelion.likelionmessengerservice.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReNameDto {
    //이름을 변경하려면 이름만 필요하다

    @NotBlank(message = "이름이 누락되었습니다")
    @Size(min = 1,max = 20,message = "이름은 최소 한글자 최대 20글자 입니다")
    private String name;
}
