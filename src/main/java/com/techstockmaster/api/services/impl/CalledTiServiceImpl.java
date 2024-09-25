package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.CalledTiDto;
import com.techstockmaster.api.domain.models.CalledTiModel;
import com.techstockmaster.api.domain.models.SectorModel;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.CalledTiRepository;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.CalledTIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalledTiServiceImpl implements CalledTIService {

    @Autowired
    private final CalledTiRepository calledTiRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final SectorRepository sectorRepository;

    public CalledTiServiceImpl(CalledTiRepository calledTiRepository, UserRepository userRepository, SectorRepository sectorRepository) {
        this.calledTiRepository = calledTiRepository;
        this.userRepository = userRepository;
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<CalledTiModel> findAll() {
        return calledTiRepository.findAll();
    }

    @Override
    public CalledTiModel findById(Integer id) {
        Optional<CalledTiModel> chamado = calledTiRepository.findById(id);
        return chamado.orElse(null);  // Retorna null se o chamado não for encontrado
    }

    @Override
    public CalledTiModel create(CalledTiDto dto) {
        // Verifica se o usuário e o setor existem
        Optional<UserModel> user = userRepository.findById(dto.idUser());
        Optional<SectorModel> sector = sectorRepository.findById(dto.idSector());

        if (user.isPresent() && sector.isPresent()) {
            // Cria uma nova entidade de chamado TI
            CalledTiModel chamado = new CalledTiModel();
            chamado.setUser(user.get());
            chamado.setSector(sector.get());
            chamado.setDescricao(dto.descricaoMensagem());
            chamado.setData(LocalDate.now()); // Define a data atual

            // Salva e retorna o chamado criado
            return calledTiRepository.save(chamado);
        } else {
            return null;  // Retorna null se o usuário ou setor não forem encontrados
        }
    }

    @Override
    public CalledTiModel delete(Integer id) {
        Optional<CalledTiModel> chamado = calledTiRepository.findById(id);
        if (chamado.isPresent()) {
            calledTiRepository.delete(chamado.get());
            return chamado.get();
        } else {
            return null; // Retorna null se o chamado não for encontrado
        }
    }

    @Override
    public CalledTiModel update(Integer id, CalledTiDto dto) {
        Optional<CalledTiModel> chamadoOptional = calledTiRepository.findById(id);
        if (chamadoOptional.isPresent()) {
            CalledTiModel chamado = chamadoOptional.get();

            // Verifica se o usuário e o setor fornecidos no DTO existem
            Optional<UserModel> user = userRepository.findById(dto.idUser());
            Optional<SectorModel> sector = sectorRepository.findById(dto.idSector());

            if (user.isPresent() && sector.isPresent()) {
                // Atualiza os dados do chamado
                chamado.setUser(user.get());
                chamado.setSector(sector.get());
                chamado.setDescricao(dto.descricaoMensagem());

                // Salva e retorna o chamado atualizado
                return calledTiRepository.save(chamado);
            }
        }
        return null; // Retorna null se o chamado não for encontrado ou se o usuário/setor não existirem
    }
}