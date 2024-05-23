package org.mjulikelion.likelionmessengerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity(name="message")
public class Message extends BaseEntity {
    //보관함에는 id와 content, sender, receiver, count 가 있다

    //id는 이미 BaseEntity에서 만들어짐

    //content
    @Setter
    @Column(nullable = false,length = 200)
    private String content;


    //sender
    @Column(nullable = false)
    private final String sender;


    //receiver
    @Column(nullable = false)
    private final String receiver;

    //messageRead
    @Setter
    @Column
    private Boolean messageRead;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
}