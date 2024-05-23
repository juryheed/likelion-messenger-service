package org.mjulikelion.likelionmessengerservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.authentication.AuthenticatedUser;
import org.mjulikelion.likelionmessengerservice.dto.ResponseDto;
import org.mjulikelion.likelionmessengerservice.dto.request.messenger.ModifyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.messenger.ReplyDto;
import org.mjulikelion.likelionmessengerservice.dto.request.messenger.SendMessengerDto;
import org.mjulikelion.likelionmessengerservice.dto.response.OneMessengerResponseData;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.service.MessengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/messengers")
public class MessengerController {
    private final MessengerService messengerService;

    //메신저 보내기
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> sendMessenger(@AuthenticatedUser User user, @RequestBody @Valid SendMessengerDto sendMessengerDto) {
        messengerService.sendMessenger(user,sendMessengerDto);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                "메신저 보내기에 성공했습니다"
        ), HttpStatus.CREATED);
    }

    //특정 메신저 보기
    @GetMapping("/{messengerid}")
    public  ResponseEntity<ResponseDto<OneMessengerResponseData>> readMessenger(@AuthenticatedUser User user, @PathVariable("messengerid") UUID messengerId){
        OneMessengerResponseData oneMessengerResponseData=messengerService.readMessenger(user,messengerId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "메신저 확인"
                ,oneMessengerResponseData
        ), HttpStatus.OK);
    }

    //(내가 발신인 일때)읽지 않은 메신저 수정하기
    @PatchMapping("/{messengerid}")
    public ResponseEntity<ResponseDto<Void>>modify(@AuthenticatedUser User user,@RequestBody ModifyDto modifyDto, @PathVariable("messengerid") UUID messengerId) {
        messengerService.modify(user,modifyDto,messengerId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                "내용을 수정했습니다"
        ), HttpStatus.CREATED);
    }

    //(내가 발신인 일때)읽지 않은 메신저 삭제하기
    @DeleteMapping("/{messengerid}")
    public ResponseEntity<ResponseDto<Void>>nonReadDelete(@AuthenticatedUser User user,@PathVariable("messengerid") UUID messengerId)
    {
        messengerService.nonReadDelete(user,messengerId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "메신저를 삭제 했습니다"
        ), HttpStatus.OK);
    }

    //답장하기
    @PostMapping("/{messengerid}")
    public ResponseEntity<ResponseDto<Void>> replyMessenger(@AuthenticatedUser User user, @RequestBody @Valid ReplyDto replyDto, @PathVariable("messengerid") UUID messengerId) {
        messengerService.reply(user,replyDto,messengerId);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                messengerId+" 에 답장을 보냈습니다"
        ), HttpStatus.CREATED);
    }


}
