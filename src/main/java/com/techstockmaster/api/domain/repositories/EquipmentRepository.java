package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.EquipmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentModel, Integer> {
}
