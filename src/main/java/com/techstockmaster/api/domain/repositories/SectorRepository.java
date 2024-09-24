package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.FeedbackModel;
import com.techstockmaster.api.domain.models.SectorModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorModal, Integer> {
    @Query("SELECT f FROM SectorModal f JOIN FETCH f.supervisor")
    List<SectorModal> findAll();

    @Query("SELECT f FROM SectorModal f JOIN FETCH f.supervisor WHERE f.id = :id")
    Optional<SectorModal> findById(Integer id);
}
