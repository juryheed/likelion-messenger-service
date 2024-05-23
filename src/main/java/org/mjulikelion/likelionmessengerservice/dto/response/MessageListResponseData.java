package org.mjulikelion.likelionmessengerservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MessageListResponseData {
    private List<MessageResponseData> messengerList;
}
