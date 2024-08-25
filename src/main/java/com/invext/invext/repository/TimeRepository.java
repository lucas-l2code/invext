package com.invext.invext.repository;

import com.invext.invext.model.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {

    Optional<TimeEntity> findByTimePadraoIsTrue();

}