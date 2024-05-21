package org.mjulikelion.likelionmessengerservice.repository;

import org.mjulikelion.likelionmessengerservice.model.Messenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessengerRepository extends JpaRepository<Messenger, UUID> {
    List<Messenger> findBySenderOrReceiver(String sender, String receiver);
}
