package com.techstockmaster.api.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techstockmaster.api.domain.models.SupervisorModel;

@Repository
public interface SupervisorRepository extends JpaRepository<SupervisorModel, Integer> {
    /*
     * Declara um método que verifica se existe um supervisor já cadastrado
     */
    boolean existsByName(String name);
}
