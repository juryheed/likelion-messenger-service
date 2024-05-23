package org.mjulikelion.likelionmessengerservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.likelionmessengerservice.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MessageResponseData {
    //발신자, 수신자, 시간, 메신저 ID, 확인 여부가 있다
    private String receiver;
    private String sender;
    private LocalDateTime time;
    private UUID messageId;
    private Boolean messageRead;
}
