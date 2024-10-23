package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.PurchaseRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequestModel, Integer> {
//    @Query("SELECT RM FROM PurchaseRequestModel RM LEFT JOIN FETCH RM.idEquipment EQ LEFT JOIN FETCH RM.idUser LG LEFT JOIN FETCH RM.idSector ST ORDER BY RM.id ASC")
//    List<PurchaseRequestModel> findAll();
//
//    @Query("SELECT RM FROM PurchaseRequestModel RM WHERE RM.id = :id")
//    Optional<PurchaseRequestModel> findById(Integer id);
}
