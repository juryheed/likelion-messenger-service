package org.mjulikelion.likelionmessengerservice.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.likelionmessengerservice.authentication.AuthenticatedUser;
import org.mjulikelion.likelionmessengerservice.dto.ResponseDto;
import org.mjulikelion.likelionmessengerservice.dto.response.MessengerListResponseData;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.service.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/boxes")
public class BoxController {
    private final BoxService boxService;

    //전체 메진저 보기
    @GetMapping
    public ResponseEntity<ResponseDto<MessengerListResponseData>> messengerAll(@AuthenticatedUser User user){
        MessengerListResponseData messengerListResponseData = boxService.messengerAll(user);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "메신저 전체 확인"
                ,messengerListResponseData
        ), HttpStatus.OK);
    }


}
