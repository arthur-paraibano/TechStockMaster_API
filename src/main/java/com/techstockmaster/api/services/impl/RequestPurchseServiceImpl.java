package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.RequestPurchseDto;
import com.techstockmaster.api.controllers.dtos.RequestPurchseUpdateDto;
import com.techstockmaster.api.domain.models.*;
import com.techstockmaster.api.domain.repositories.EquipmentRepository;
import com.techstockmaster.api.domain.repositories.RequestPurchseRepository;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.RequestPurchseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RequestPurchseServiceImpl implements RequestPurchseService {

    @Autowired
    private final RequestPurchseRepository repository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EquipmentRepository equipmentRepository;

    @Autowired
    private final SectorRepository sectorRepository;

    public RequestPurchseServiceImpl(RequestPurchseRepository repository, UserRepository userRepository, EquipmentRepository equipmentRepository, SectorRepository sectorRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<RequestPurchseModel> findAll() {
        return repository.findAll();
    }

    @Override
    public RequestPurchseModel findById(Integer id) {
        Optional<RequestPurchseModel> entity = repository.findById(id);
        return entity.orElse(null);
    }

    @Override
    public RequestPurchseModel create(RequestPurchseDto dto) {
        if (dto.quantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que 0!");
        }

        EquipmentModel equip = equipmentRepository.findById(dto.idEquipamento())
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        UserModel user = userRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        SectorModel sector = sectorRepository.findById(dto.idSector())
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));

        String status = "SOLICITADO";
        RequestPurchseModel mov = new RequestPurchseModel();
        mov.setIdEquipment(equip);
        mov.setQuantity(dto.quantidade());
        mov.setIdSector(sector);
        mov.setIdUser(user);
        mov.setDescription(dto.descricao());
        mov.setDate(LocalDate.now());
        mov.setStatus(status);
        return repository.save(mov);
    }

    @Override
    public RequestPurchseModel delete(Integer integer) {
        return null;
    }

    @Override
    public RequestPurchseModel update(Integer integer, RequestPurchseDto entity) {
        return null;
    }

    public RequestPurchseModel updateStatus(Integer id, RequestPurchseUpdateDto dto) {
        Optional<RequestPurchseModel> existEquip = repository.findById(id);
        if (!existEquip.isPresent()) {
            throw new IllegalArgumentException("Solicitação não existe");
        }
        String status =  dto.status().toUpperCase();
        if (!status.matches("AGUARDANDO ENVIO|EM CONSERTO|RECEBIDO")) {
            throw new IllegalArgumentException("O status deve ser AGUARDANDO ENVIO, EM CONSERTO ou RECEBIDO");
        }
        RequestPurchseModel mov = existEquip.get();
        mov.setStatus(dto.status());
        return repository.save(mov);
    }
}
