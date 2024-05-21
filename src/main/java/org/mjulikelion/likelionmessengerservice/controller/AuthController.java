package org.mjulikelion.likelionmessengerservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.likelionmessengerservice.authentication.AuthenticatedUser;
import org.mjulikelion.likelionmessengerservice.authentication.JwtTokenProvider;
import org.mjulikelion.likelionmessengerservice.dto.ResponseDto;
import org.mjulikelion.likelionmessengerservice.dto.request.LoginDto;
import org.mjulikelion.likelionmessengerservice.dto.request.SignupDto;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.service.AuthService;
import org.mjulikelion.likelionmessengerservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>>signup(@RequestBody @Valid SignupDto signupDto, HttpServletResponse response){

        authService.signup(signupDto);

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                "회원 가입 완료"
        ), HttpStatus.CREATED);

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>>login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response){
        authService.login(loginDto);

        String payload = userService.findUser(loginDto).getId().toString();
        String accessToken = jwtTokenProvider.createToken(payload);

        //쿠키 만드는거
        ResponseCookie cookie = ResponseCookie.from("AccessToken", "Bearer+" + accessToken) //인코딩, 쿠키이름과 쿠키값을 지정한다
                .maxAge(Duration.ofMillis(1800000))
                .path("/")
                .build();
        response.addHeader("set-cookie", cookie.toString());

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "로그인 완료:"
        ), HttpStatus.OK);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(final HttpServletResponse response, @AuthenticatedUser User user) {
        ResponseCookie cookie = ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .path("/")
                .build();
        response.addHeader("set-cookie", cookie.toString());

        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "로그 아웃 완료"
        ), HttpStatus.OK);
    }


}
