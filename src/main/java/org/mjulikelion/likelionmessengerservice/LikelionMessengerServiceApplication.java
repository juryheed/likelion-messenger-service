package org.mjulikelion.likelionmessengerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LikelionMessengerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikelionMessengerServiceApplication.class, args);
    }

}
