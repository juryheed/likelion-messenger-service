package org.mjulikelion.likelionmessengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplyDto {
    //답장을 위한 Dto
    @NotBlank(message = "내용이 누락됐습니다")
    @Size(min=1,max = 200,message = "최소 1글자 최대 200글자 입니다")
    private String content;
}
