package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.MovementDto;
import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.MovementRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.MovementService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private final MovementRepository repository;

    @Autowired
    private final UserRepository userRepository;

    public MovementServiceImpl(MovementRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MovementModel> findAll() {
        return repository.findAll();
    }

    @Override
    public MovementModel findById(Integer id) {
        Optional<MovementModel> movment = repository.findById(id);
        return movment.orElse(null);
    }

    @Override
    public MovementModel create(MovementDto dto) {
        String status = dto.nLykos();
        if (!status.matches("IN|OUT")) {
            throw new IllegalArgumentException("Relevância deve ser ALTO, MÉDIO ou BAIXO");
        }

        MovementModel mov = new MovementModel();
        //  mov.setDate(LocalDate.now());

        // Buscar usuário pelo ID
        UserModel user = userRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));


        return repository.save(mov);
    }

    @Override
    public MovementModel delete(Integer integer) {
        return null;
    }

    @Override
    public MovementModel update(Integer integer, MovementDto entity) {
        return null;
    }
}
