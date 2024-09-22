package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.TagModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagModal, Integer> {
    boolean existsByAbrevTag(String abrevTag);

    @Query(value = "SELECT COUNT(1) FROM bd_estoque.tag TG WHERE TG.DESC_TAG = :descTag", nativeQuery = true)
    Long countByDescTag(String descTag);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM bd_estoque.equipamento EQ LEFT JOIN bd_estoque.tag TG ON EQ.FK_TAG = TG.ID WHERE TG.ID = :id)", nativeQuery = true)
    Long existsTagInEquipamento(Integer id);
}