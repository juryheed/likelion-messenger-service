package org.mjulikelion.likelionmessengerservice.controller;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.mjulikelion.likelionmessengerservice.authentication.AuthenticatedUser;
//import org.mjulikelion.likelionmessengerservice.dto.ResponseDto;
//
//import org.mjulikelion.likelionmessengerservice.dto.request.message.ModifyDto;
//import org.mjulikelion.likelionmessengerservice.dto.request.message.ReplyDto;
//import org.mjulikelion.likelionmessengerservice.dto.request.message.SendMessageDto;
//import org.mjulikelion.likelionmessengerservice.dto.response.OneMessageResponseData;
//import org.mjulikelion.likelionmessengerservice.model.User;
//import org.mjulikelion.likelionmessengerservice.service.MessageService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.authentication.AuthenticatedUser;
import org.mjulikelion.likelionmessengerservice.dto.ResponseDto;
import org.mjulikelion.likelionmessengerservice.dto.request.message.ModifyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.message.ReplyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.message.SendMessageDto;
import org.mjulikelion.likelionmessengerservice.dto.response.OneMessageResponseData;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    //메신저 보내기
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> sendMessage(@AuthenticatedUser User user, @RequestBody @Valid SendMessageDto sendMessageDto) {
        messageService.sendMessage(user,sendMessageDto);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                "메신저 보내기에 성공했습니다"
        ), HttpStatus.CREATED);
    }

    //특정 메신저 보기
    @GetMapping("/{messageid}")
    public  ResponseEntity<ResponseDto<OneMessageResponseData>> readMessage(@AuthenticatedUser User user, @PathVariable("messageid") UUID messageId){
        OneMessageResponseData oneMessageResponseData=messageService.readMessage(user,messageId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "메신저 확인"
                ,oneMessageResponseData
        ), HttpStatus.OK);
    }

    //(내가 발신인 일때)읽지 않은 메신저 수정하기
    @PatchMapping("/{messageid}")
    public ResponseEntity<ResponseDto<Void>>modify(@AuthenticatedUser User user, @RequestBody ModifyDto modifyDto, @PathVariable("messageid") UUID messageId) {
        messageService.modify(user,modifyDto,messageId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                "내용을 수정했습니다"
        ), HttpStatus.CREATED);
    }

    //(내가 발신인 일때)읽지 않은 메신저 삭제하기
    @DeleteMapping("/{messageid}")
    public ResponseEntity<ResponseDto<Void>>nonReadDelete(@AuthenticatedUser User user,@PathVariable("messageid") UUID messageId)
    {
        messageService.nonReadDelete(user,messageId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "메신저를 삭제 했습니다"
        ), HttpStatus.OK);
    }

    //답장하기
    @PostMapping("/{messageid}")
    public ResponseEntity<ResponseDto<Void>> replyMessenger(@AuthenticatedUser User user, @RequestBody @Valid ReplyDto replyDto, @PathVariable("messageid") UUID messageId) {
        messageService.reply(user,replyDto,messageId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                messageId+" 에 답장을 보냈습니다"
        ), HttpStatus.CREATED);
    }


}
