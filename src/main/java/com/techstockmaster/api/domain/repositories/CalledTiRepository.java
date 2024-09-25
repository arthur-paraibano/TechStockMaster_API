package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.CalledTiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalledTiRepository extends JpaRepository<CalledTiModel, Integer> {
}
