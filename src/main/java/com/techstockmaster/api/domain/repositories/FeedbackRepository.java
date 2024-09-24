package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Integer> {
    @Query("SELECT f FROM FeedbackModel f JOIN FETCH f.user")
    List<FeedbackModel> findAll();

    @Query("SELECT f FROM FeedbackModel f JOIN FETCH f.user WHERE f.id = :id")
    Optional<FeedbackModel> findById(Integer id);
}
