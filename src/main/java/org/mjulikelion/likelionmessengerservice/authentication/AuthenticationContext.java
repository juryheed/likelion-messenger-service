package org.mjulikelion.likelionmessengerservice.authentication;

import lombok.Getter;
import lombok.Setter;
import org.mjulikelion.likelionmessengerservice.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Setter
@Getter
@Component
@RequestScope//요청이 들어오면 생성되고, 요청이 끝나면 제거된다
public class AuthenticationContext {
    private User principal;
}

