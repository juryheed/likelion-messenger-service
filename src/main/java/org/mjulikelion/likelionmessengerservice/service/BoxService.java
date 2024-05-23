package org.mjulikelion.likelionmessengerservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.dto.response.*;
import org.mjulikelion.likelionmessengerservice.model.Message;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.MessageRepository;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BoxService {
    private MessageRepository messageRepository;

    // 전체 메신저 기록 보기
    public MessageListResponseData messageAll(User user) {
        // 내가 수신,발신한 모든 메신저 찾기
        List<Message> Messages = messageRepository.findBySenderOrReceiver(user.getEmployeeNumber(), user.getEmployeeNumber());

        List<MessageResponseData> messageResponseList = new LinkedList<>();

        for (Message m : Messages) {
            MessageResponseData messageResponseData = MessageResponseData.builder()
                    .sender(m.getSender())
                    .messageId(m.getId())
                    .receiver(m.getReceiver())
                    .messageRead(m.getMessageRead())
                    .time(m.getUpdatedAt())
                    .build();
            messageResponseList.add(messageResponseData);
        }

        MessageListResponseData messageListResponseData= MessageListResponseData.builder()
                .messengerList(messageResponseList)
                .build();

        return messageListResponseData;
    }

}
