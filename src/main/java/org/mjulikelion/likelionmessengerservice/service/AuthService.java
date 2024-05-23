package org.mjulikelion.likelionmessengerservice.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.likelionmessengerservice.authentication.PasswordHashEncryption;
import org.mjulikelion.likelionmessengerservice.dto.request.user.LoginDto;
import org.mjulikelion.likelionmessengerservice.dto.request.user.SignupDto;
import org.mjulikelion.likelionmessengerservice.error.ErrorCode;
import org.mjulikelion.likelionmessengerservice.error.exception.ConflictException;
import org.mjulikelion.likelionmessengerservice.error.exception.ForbiddenException;
import org.mjulikelion.likelionmessengerservice.error.exception.NotFoundException;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    //로그인
    public void login(LoginDto loginDto){
        //유저 찾기
        User user=userRepository.findByEmployeeNumber(loginDto.getEmployeeNumber());
        if(null==user){
            throw new NotFoundException(ErrorCode.EMPLOIEE_NOT_FOUND);
        }

        //직원아이디와 비밀번호 일치 확인
        if(!passwordHashEncryption.matches(loginDto.getPassword(), user.getPassword())){
            throw new ForbiddenException(ErrorCode.PASSWORD_NOT_EQUAL);
        }
    }

    //회원 가입
    public void signup(SignupDto signupDto){
        String plainPassword=signupDto.getPassword();
        String hashedPassword=passwordHashEncryption.encrypt(plainPassword);

        //직원아이디 중복 확인
        User user=userRepository.findByEmployeeNumber(signupDto.getEmployeeNumber());
        if(null!=user){
            throw new ConflictException(ErrorCode.EMPLOIEE_DUPLICATION);
        }

        //유저 만들어주기
        User newUser = User.builder()
                .name(signupDto.getName())
                .employeeNumber(signupDto.getEmployeeNumber())
                .password(hashedPassword)   //암호화 된 Password가 들어감
                .build();
        userRepository.save(newUser);
    }
}
