package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.RequestPurchseModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestPurchseRepository extends JpaRepository<RequestPurchseModel, Integer> {

    @Query("SELECT RP FROM RequestPurchseModel RP LEFT JOIN FETCH RP.idEquipment EQ LEFT JOIN FETCH RP.idUser LG LEFT JOIN FETCH RP.idSector ST ORDER BY RP.id ASC")
    List<RequestPurchseModel> findAll();

    @Query("SELECT RP FROM RequestPurchseModel RP WHERE RP.id = :id")
    Optional<RequestPurchseModel> findById(Integer id);
}
