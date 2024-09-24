package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.SectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorModel, Integer> {
    @Query("SELECT f FROM SectorModel f JOIN FETCH f.supervisor")
    List<SectorModel> findAll();

    @Query("SELECT f FROM SectorModel f JOIN FETCH f.supervisor WHERE f.id = :id")
    Optional<SectorModel> findById(Integer id);
}
