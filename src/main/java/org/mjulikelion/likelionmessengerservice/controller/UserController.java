package org.mjulikelion.likelionmessengerservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.likelionmessengerservice.authentication.AuthenticatedUser;
import org.mjulikelion.likelionmessengerservice.dto.ResponseDto;
import org.mjulikelion.likelionmessengerservice.dto.request.ReNameDto;
import org.mjulikelion.likelionmessengerservice.dto.request.RePasswordDto;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //사용자 이름 수정
    @PatchMapping("/rename")
    public ResponseEntity<ResponseDto<Void>> rename(@AuthenticatedUser User user, @RequestBody @Valid ReNameDto reNameDto){
        userService.reName(user, reNameDto);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "이름 수정 완료"
        ), HttpStatus.OK);
    }

    //비밀번호 수정
    @PatchMapping("/repassword")
    public ResponseEntity<ResponseDto<Void>>repassword(@AuthenticatedUser User user,@RequestBody @Valid RePasswordDto rePasswordDto){
        userService.rePassword(user,rePasswordDto);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "비밀번호 변경 완료"
        ), HttpStatus.OK);
    }

    //회원 탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>>delete(final HttpServletResponse response,@AuthenticatedUser User user){
        userService.delete(user);

        ResponseCookie cookie = ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .path("/")
                .build();
        response.addHeader("set-cookie", cookie.toString());

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "회원 탈퇴 완료"
        ), HttpStatus.OK);
    }
}
