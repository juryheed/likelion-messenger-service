package org.mjulikelion.likelionmessengerservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity(name="user")
public class User extends BaseEntity {
    //유저는 id,name,companyId,password가 있다.

    //유저 id는 BaseEntity에서 생성

    //name
    @Setter
    @Column(nullable = false,length = 20)
    private String name;

    //employeeNumber
    @Column(nullable = false,length = 20,unique = true)
    private final String employeeNumber;

    //password
    @Setter
    @Column(nullable = false,length = 100)
    private String password;

    // message
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> message;
}