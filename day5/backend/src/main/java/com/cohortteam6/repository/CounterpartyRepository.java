package com.cohortteam6.repository;

import com.cohortteam6.repository.entity.Counterparty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounterpartyRepository extends JpaRepository<Counterparty, Long> {
    Optional<Counterparty> findByLeiCode(String leiCode);
}
