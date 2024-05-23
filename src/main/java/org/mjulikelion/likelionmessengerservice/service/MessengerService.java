package org.mjulikelion.likelionmessengerservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.dto.request.messenger.ModifyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.messenger.ReplyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.messenger.SendMessengerDto;
import org.mjulikelion.likelionmessengerservice.dto.response.OneMessengerResponseData;
import org.mjulikelion.likelionmessengerservice.error.ErrorCode;
import org.mjulikelion.likelionmessengerservice.error.exception.ForbiddenException;
import org.mjulikelion.likelionmessengerservice.error.exception.NotFoundException;
import org.mjulikelion.likelionmessengerservice.model.Messenger;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.MessengerRepository;
import org.mjulikelion.likelionmessengerservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MessengerService {
    private MessengerRepository messengerRepository;
    private UserRepository userRepository;

    //메신저 보내기
    public void sendMessenger(User user, SendMessengerDto sendMessengerDto){

        List<String> recievers = sendMessengerDto.getReceiver();    //받는건 여러명인데
        //수신자 존재 검사
        for(String r: recievers){
            findUser(r);
        }
        //메신저 보내기
        for(String r: recievers){
            Messenger messenger=Messenger.builder()
                    .content(sendMessengerDto.getContent())
                    .sender(user.getCompanyId())
                    .receiver(r)        //한명씩 저장되는거
                    .count("안읽음")
                    .user(user)
                    .build();
            messengerRepository.save(messenger);
        }
    }

    //특정 메신저 읽기
    public OneMessengerResponseData readMessenger(User user, UUID messengerId){
        //메신저 아이디로 메시지 찾기
        Messenger messenger=findMessenger(messengerId);

        //내가 이 메신저의 발신자와 수신자만 확인가능
        checkSenderAndReceiver(user,messenger);

        //수신자인 경우만 읽음 표시
        if(messenger.getReceiver().equals(user.getCompanyId()))
        {
            messenger.setCount("읽음");
            messengerRepository.save(messenger);
        }

        //읽을 메신저 생성
        OneMessengerResponseData oneMessengerResponseData=OneMessengerResponseData.builder()
                .content(messenger.getContent())
                .sender(messenger.getSender())
                .time(messenger.getCreatedAt())
                .receiver(messenger.getReceiver())
                .messengerId(messengerId)
                .build();
        return oneMessengerResponseData;
    }

    //(내가 발신인 일때)읽지 않은 메신저 수정하기
    public void modify(User user, ModifyDto modifyDto, UUID messengerId){
        //메신저 아이디로 메시지 찾기
        Messenger messenger=findMessenger(messengerId);

        //내가 발신자 인지 확인
        checkSender(user,messenger);

        //읽었는지 확인하기
        checkRead(messenger);

        //내용 수정하기
        messenger.setContent(modifyDto.getContent());

        //메신저 저장하기
        messengerRepository.save(messenger);
    }

    //(내가 발신인 일때)읽지 않은 메신저 삭제하기
    public void nonReadDelete(User user,UUID messengerId){
        //메신저 아이디로 메시지 찾기
        Messenger messenger=findMessenger(messengerId);

        //내가 발신자가 맞는지 확인
        checkSender(user,messenger);

        //읽었는지 확인하기
        checkRead(messenger);

        //삭제 하기
        messengerRepository.delete(messenger);
    }

    //답장하기
    public void reply(User user, ReplyDto replyDto, UUID messengerId){
        //메시지 아이디로 메신저 찾기
        Messenger messenger=findMessenger(messengerId);

        //내가 수신자인지 획인 하기
        checkReceiver(user,messenger);

        findUser(messenger.getSender());

        //찾은 메시지의 발신자와 수신자를 서로 바꿔서 메시지 작성하기
        Messenger newmessenger=Messenger.builder()
                .content(replyDto.getContent())
                .sender(user.getCompanyId())
                .receiver(messenger.getSender())
                .count("안읽음")
                .user(user)
                .build();
        messengerRepository.save(newmessenger);
    }

    //메신저 아이디로 메신저를 찾는 메서드
    private Messenger findMessenger(UUID messengerId){
        Messenger messenger=messengerRepository.findById(messengerId).orElseThrow(() -> new NotFoundException(ErrorCode.MESSENGER_NOT_FOUND));
        return messenger;
    }

    //발신자 확인 메서드
    private void checkSender(User user,Messenger messenger){
        if(!(user.getCompanyId().equals(messenger.getSender()))){
            throw new ForbiddenException(ErrorCode.CANT_ACCESS);
        }
    }

    //수신자 획인 메서드
    private void checkReceiver(User user,Messenger messenger){
        if(!(user.getCompanyId().equals(messenger.getReceiver()))){
            throw new ForbiddenException(ErrorCode.CANT_ACCESS);
        }
    }

    //수신자와 발신자 확인 메서드
    private void checkSenderAndReceiver(User user,Messenger messenger){
        if(!(user.getCompanyId().equals(messenger.getReceiver()))){

            if(!(user.getCompanyId().equals(messenger.getSender()))){
                //발신자도 아니고 수신자도 아닌 경우
                throw new ForbiddenException(ErrorCode.CANT_ACCESS);
            }
        }
    }

    //읽었는지 확인 하기
    private void checkRead(Messenger messenger){
        if(!(messenger.getCount().equals("안읽음"))){
            throw new ForbiddenException(ErrorCode.CANT_ACCESS);
        }
    }

    public void findUser(String companyId){
        User user=userRepository.findByCompanyId(companyId);
        if(user==null){
            throw new NotFoundException(ErrorCode.RECIEVER_NOT_FOUND);
        }
    }
}
