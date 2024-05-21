package org.mjulikelion.likelionmessengerservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MessengerResponseData {
    //발신인, 시간, 메신저 ID가 있다
    private String receiver;
    private String sender;
    private LocalDateTime time;
    private UUID messengerId;
    private String count;
}
