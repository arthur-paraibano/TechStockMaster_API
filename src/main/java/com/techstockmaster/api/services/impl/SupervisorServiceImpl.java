package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.SupervisorDto;
import com.techstockmaster.api.domain.models.SupervisorModel;
import com.techstockmaster.api.domain.repositories.SupervisorRepository;
import com.techstockmaster.api.services.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Aqui estão todos os métodos com conexões com o banco de dados e algumas validações
 */
@Service
public class SupervisorServiceImpl implements SupervisorService {

    // Injeção da classe UserRepository
    @Autowired
    private final SupervisorRepository supervisorRepository;

    public SupervisorServiceImpl(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    // Puxa tudo que existir na tabela informada no Modal
    public List<SupervisorModel> findAll() {
        return supervisorRepository.findAll();
    }

    // Puxa apenas o elemento que corresponder ao ID
    public SupervisorModel findById(Integer id) {
        Optional<SupervisorModel> user = supervisorRepository.findById(id);
        return user.orElse(null);
    }

    /*
     * Cria um novo supervisor, verifica se já existe um cadastrado
     */
    public SupervisorModel create(SupervisorDto dto) {
        if (supervisorRepository.existsByName(dto.name())) {
            throw new DataIntegrityViolationException("Supervisor já existe");
        }
        SupervisorModel supervisor = new SupervisorModel();
        supervisor.setName(dto.name().toUpperCase());
        return supervisorRepository.save(supervisor);
    }

    // Metodo para excluir supervisor passando o ID dele
    public SupervisorModel delete(Integer id) {
        Optional<SupervisorModel> user = supervisorRepository.findById(id);
        supervisorRepository.delete(user.get());
        return user.orElse(null);
    }

    // Metodo para fazer alterações passando o ID do Supervisor
    public SupervisorModel update(Integer id, SupervisorDto dto) {
        Optional<SupervisorModel> existingSupervisorOpt = supervisorRepository.findById(id);
        if (!existingSupervisorOpt.isPresent()) {
            return null;
        }
        SupervisorModel existingUser = existingSupervisorOpt.get();

        if (supervisorRepository.existsByName(dto.name()) &&
                !existingUser.getName().equals(dto.name())) {
            throw new DataIntegrityViolationException("Nome já existe!");
        }

        existingUser.setName(dto.name().toUpperCase());
        return supervisorRepository.save(existingUser);
    }
}