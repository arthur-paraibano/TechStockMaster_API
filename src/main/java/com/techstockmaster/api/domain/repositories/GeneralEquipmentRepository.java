package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.GeneralEquipmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralEquipmentRepository extends JpaRepository<GeneralEquipmentModel, Integer> {
}