package org.mjulikelion.likelionmessengerservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.dto.response.*;
import org.mjulikelion.likelionmessengerservice.model.Messenger;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.MessengerRepository;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BoxService {
    private MessengerRepository messengerRepository;

    // 전체 메신저 기록 보기
    public MessengerListResponseData messengerAll(User user) {
        // 내가 수신,발신한 모든 메신저 찾기
        List<Messenger> messengers = messengerRepository.findBySenderOrReceiver(user.getCompanyId(), user.getCompanyId());

        List<MessengerResponseData> messengerResponseList = new LinkedList<>();

        for (Messenger m : messengers) {
            MessengerResponseData messengerResponseData = MessengerResponseData.builder()
                    .sender(m.getSender())
                    .messengerId(m.getId())
                    .receiver(m.getReceiver())
                    .count(m.getCount())
                    .time(m.getUpdatedAt())
                    .build();
            messengerResponseList.add(messengerResponseData);
        }

        MessengerListResponseData messengerListResponseData=MessengerListResponseData.builder()
                .messengerList(messengerResponseList)
                .build();

        return messengerListResponseData;
    }

}
