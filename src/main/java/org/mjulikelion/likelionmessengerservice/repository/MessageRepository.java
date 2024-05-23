package org.mjulikelion.likelionmessengerservice.repository;

import org.mjulikelion.likelionmessengerservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySenderOrReceiver(String sender, String receiver);
}
