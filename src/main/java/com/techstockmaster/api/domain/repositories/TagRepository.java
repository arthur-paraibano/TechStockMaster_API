package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.domain.models.RepairModel;
import com.techstockmaster.api.domain.models.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagModel, Integer> {

    @Query("SELECT t FROM TagModel t")
    List<TagModel> findAll();

    @Query("SELECT TM FROM TagModel TM WHERE TM.id = :id")
    Optional<TagModel> findById(Integer id);

    boolean existsByAbrevTag(String abrevTag);

    @Query(value = "SELECT COUNT(1) FROM bd_estoque.tag TG WHERE TG.DESC_TAG = :descTag", nativeQuery = true)
    Long countByDescTag(String descTag);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM bd_estoque.equipamento EQ LEFT JOIN bd_estoque.tag TG ON EQ.FK_TAG = TG.ID WHERE TG.ID = :id)", nativeQuery = true)
    Long existsTagInEquipamento(Integer id);
}