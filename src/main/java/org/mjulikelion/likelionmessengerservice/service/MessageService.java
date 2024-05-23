package org.mjulikelion.likelionmessengerservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.dto.request.message.ModifyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.message.ReplyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.message.SendMessageDto;
import org.mjulikelion.likelionmessengerservice.dto.response.OneMessageResponseData;
import org.mjulikelion.likelionmessengerservice.error.ErrorCode;
import org.mjulikelion.likelionmessengerservice.error.exception.ForbiddenException;
import org.mjulikelion.likelionmessengerservice.error.exception.NotFoundException;
import org.mjulikelion.likelionmessengerservice.model.Message;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.MessageRepository;
import org.mjulikelion.likelionmessengerservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    //메신저 보내기
    public void sendMessage(User user, SendMessageDto sendMessageDto){

        List<String> recievers = sendMessageDto.getReceiver();    //받는건 여러명인데

        //수신자 존재 검사
        for(String r: recievers){
            findUser(r);
        }
        log.info(user.getEmployeeNumber());
        //메신저 보내기
        for(String r: recievers){
            Message message= Message.builder()
                    .content(sendMessageDto.getContent())
                    .sender(user.getEmployeeNumber())
                    .receiver(r)        //한명씩 저장되는거
                    .messageRead(false)
                    .user(user)
                    .build();
            messageRepository.save(message);
        }
    }


    //특정 메신저 읽기
    public OneMessageResponseData readMessage(User user, UUID messageId){
        //메신저 아이디로 메시지 찾기
        Message message=findMessage(messageId);

        //내가 이 메신저의 발신자와 수신자만 확인가능
        checkSenderAndReceiver(user,message);

        //수신자인 경우만 읽음 표시
        if(message.getReceiver().equals(user.getEmployeeNumber()))
        {
            message.setMessageRead(true);
            messageRepository.save(message);
        }

        //읽을 메신저 생성
        OneMessageResponseData oneMessageResponseData=OneMessageResponseData.builder()
                .content(message.getContent())
                .sender(message.getSender())
                .time(message.getCreatedAt())
                .receiver(message.getReceiver())
                .messengerId(messageId)
                .build();
        return oneMessageResponseData;
    }

    //(내가 발신인 일때)읽지 않은 메신저 수정하기
    public void modify(User user, ModifyDto modifyDto, UUID messageId){
        //메신저 아이디로 메시지 찾기
        Message message=findMessage(messageId);

        //내가 발신자 인지 확인
        checkSender(user,message);

        //읽었는지 확인하기
        checkRead(message);

        //내용 수정하기
        message.setContent(modifyDto.getContent());

        //메신저 저장하기
        messageRepository.save(message);
    }

    //(내가 발신인 일때)읽지 않은 메신저 삭제하기
    public void nonReadDelete(User user,UUID messageId){
        //메신저 아이디로 메시지 찾기
        Message message=findMessage(messageId);

        //내가 발신자가 맞는지 확인
        checkSender(user,message);

        //읽었는지 확인하기
        checkRead(message);

        //삭제 하기
        messageRepository.delete(message);
    }

    //답장하기
    public void reply(User user, ReplyDto replyDto, UUID messengerId){
        //메시지 아이디로 메신저 찾기
        Message message=findMessage(messengerId);

        //내가 수신자인지 획인 하기
        checkReceiver(user,message);

        //발신자가 아직 있는지 확인
        findUser(message.getSender());

        //찾은 메시지의 발신자와 수신자를 서로 바꿔서 메시지 작성하기
        Message newMessage= Message.builder()
                .content(replyDto.getContent())
                .sender(user.getEmployeeNumber())
                .receiver(message.getSender())
                .messageRead(false)
                .user(user)
                .build();
        messageRepository.save(newMessage);
    }

    //메신저 아이디로 메신저를 찾는 메서드
    private Message findMessage(UUID messengerId){
        Message message=messageRepository.findById(messengerId).orElseThrow(() -> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
        return message;
    }

    //발신자 확인 메서드
    private void checkSender(User user, Message message){
        if(!(user.getEmployeeNumber().equals(message.getSender()))){
            throw new ForbiddenException(ErrorCode.CANT_ACCESS);
        }
    }

    //수신자 획인 메서드
    private void checkReceiver(User user, Message message){
        if(!(user.getEmployeeNumber().equals(message.getReceiver()))){
            throw new ForbiddenException(ErrorCode.CANT_ACCESS);
        }
    }

    //수신자와 발신자 확인 메서드
    private void checkSenderAndReceiver(User user, Message message){
        if(!(user.getEmployeeNumber().equals(message.getReceiver()))){

            if(!(user.getEmployeeNumber().equals(message.getSender()))){
                //발신자도 아니고 수신자도 아닌 경우
                throw new ForbiddenException(ErrorCode.CANT_ACCESS);
            }
        }
    }

    //읽었는지 확인 하기
    private void checkRead(Message message){
        if(!(message.getMessageRead().equals(false))){
            throw new ForbiddenException(ErrorCode.CANT_ACCESS);
        }
    }

    //직원의 존재 확인
    public void findUser(String employeeNumber){
        User user=userRepository.findByEmployeeNumber(employeeNumber);
        if(user==null){
            throw new NotFoundException(ErrorCode.RECIEVER_NOT_FOUND);
        }
    }
}
