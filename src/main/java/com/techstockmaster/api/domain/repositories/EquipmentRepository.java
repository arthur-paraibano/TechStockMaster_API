package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.EquipmentModel;
import com.techstockmaster.api.domain.models.SectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentModel, Integer> {

    @Query("SELECT e FROM EquipmentModel e JOIN FETCH e.idSector s JOIN FETCH e.tag t WHERE e.id = :id")
    Optional<EquipmentModel> findByIdEquipm(@Param("id") Integer id);
}
