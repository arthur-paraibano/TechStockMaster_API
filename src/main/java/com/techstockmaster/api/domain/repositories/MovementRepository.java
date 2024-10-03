package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.domain.models.SectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<MovementModel, Integer> {
    @Query("SELECT MV FROM MovementModel MV LEFT JOIN FETCH MV.idEquipment EQ LEFT JOIN FETCH MV.idUser LG LEFT JOIN FETCH MV.idSector ST ORDER BY MV.id ASC")
    List<MovementModel> findAll();

    @Query("SELECT MV FROM MovementModel MV WHERE MV.id = :id")
    Optional<MovementModel> findById(Integer id);
}
