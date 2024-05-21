package org.mjulikelion.likelionmessengerservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class OneMessengerResponseData {
    //발신인, 내용 , 시간, 메신저ID가 있다
    private String receiver;
    private String sender;
    private String content;
    private LocalDateTime time;
    private UUID messengerId;
}
