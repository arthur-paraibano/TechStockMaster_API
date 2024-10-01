package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.MovementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<MovementModel, Integer> {
}
