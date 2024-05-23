package org.mjulikelion.likelionmessengerservice.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.likelionmessengerservice.authentication.PasswordHashEncryption;
import org.mjulikelion.likelionmessengerservice.dto.request.user.LoginDto;
import org.mjulikelion.likelionmessengerservice.dto.request.user.ReNameDto;
import org.mjulikelion.likelionmessengerservice.dto.request.user.RePasswordDto;
import org.mjulikelion.likelionmessengerservice.error.ErrorCode;
import org.mjulikelion.likelionmessengerservice.error.exception.ForbiddenException;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    public void reName(User user, ReNameDto reNameDto){
        //이름 변경
        user.setName(reNameDto.getName());
        userRepository.save(user);
    }

    //비밀번호 변경
    public void rePassword(User user, RePasswordDto rePasswordDto){
        //유저가 현재 비빌 번호를 알고 있는지 검사
        String plainPassword = rePasswordDto.getNowPassword();
        if(!passwordHashEncryption.matches(plainPassword, user.getPassword())){
            throw new ForbiddenException(ErrorCode.PASSWORD_NOT_EQUAL);
        }

        //비밀번호 변경
        String hashedPassword = passwordHashEncryption.encrypt(rePasswordDto.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public void delete(User user){
        //회원 삭제
        userRepository.delete(user);
    }


    public User findUser(LoginDto loginDto){
        User user=userRepository.findByCompanyId(loginDto.getCompanyId());
        return user;
    }
}
