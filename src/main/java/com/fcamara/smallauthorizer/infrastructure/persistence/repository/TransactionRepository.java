package com.fcamara.smallauthorizer.infrastructure.persistence.repository;

import com.fcamara.smallauthorizer.infrastructure.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
