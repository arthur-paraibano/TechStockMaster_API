package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Integer> {
}
