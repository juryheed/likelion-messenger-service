package org.mjulikelion.likelionmessengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SendMessengerDto {
    //보내는 메신저에는 발신인, 수신인, 내용 이 있다

    /*
    @NotBlank(message = "발신인이 누락되었습니다")
    private String sender;
     */

    private List<String> receiver;

    @NotBlank(message = "내용이 누락됐습니다")
    @Size(min=1,max = 200,message = "최소 1글자 최대 200글자 입니다")
    private String content;

}
