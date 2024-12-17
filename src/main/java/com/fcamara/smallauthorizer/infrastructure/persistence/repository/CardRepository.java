package com.fcamara.smallauthorizer.infrastructure.persistence.repository;

import com.fcamara.smallauthorizer.infrastructure.persistence.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByNumber(String number);

}
