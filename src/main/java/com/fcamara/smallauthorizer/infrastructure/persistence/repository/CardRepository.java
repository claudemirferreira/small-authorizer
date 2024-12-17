package com.fcamara.smallauthorizer.infrastructure.persistence.repository;

import com.fcamara.smallauthorizer.infrastructure.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    Optional<CardEntity> findByNumber(String number);

}
