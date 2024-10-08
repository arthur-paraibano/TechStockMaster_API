package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.RepairModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<RepairModel, Integer> {
    @Query("SELECT RM FROM RepairModel RM LEFT JOIN FETCH RM.idEquipment EQ LEFT JOIN FETCH RM.idUser LG LEFT JOIN FETCH RM.idSector ST ORDER BY RM.id ASC")
    List<RepairModel> findAll();

    @Query("SELECT RM FROM RepairModel RM WHERE RM.id = :id")
    Optional<RepairModel> findById(Integer id);
}
