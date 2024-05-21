package org.mjulikelion.likelionmessengerservice.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.likelionmessengerservice.error.ErrorCode;
import org.mjulikelion.likelionmessengerservice.error.exception.NotFoundException;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.mjulikelion.likelionmessengerservice.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        //토큰 가져오기
        String accessToken = AuthenticationExtractor.extract(request);  //쿠키에서 쿠키값을 가져옴
        //토큰으로 userId찾기
        UUID userId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        //userId로 유저 찾기
        User user = findExistingUser(userId);
        authenticationContext.setPrincipal(user);
        return true;
    }

    private User findExistingUser(final UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMPANYID_NOT_FOUND));
    }
}
