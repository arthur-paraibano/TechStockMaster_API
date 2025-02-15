package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.MovementDto;
import com.techstockmaster.api.domain.models.EquipmentModel;
import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.domain.models.SectorModel;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.EquipmentRepository;
import com.techstockmaster.api.domain.repositories.MovementRepository;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.MovementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private final MovementRepository repository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EquipmentRepository equipmentRepository;

    @Autowired
    private final SectorRepository sectorRepository;

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
        String status = dto.type();
        if (!status.matches("IN|OUT")) {
            throw new IllegalArgumentException("O typo deve ser IN ou OUT");
        }
        if (dto.quantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        }
        Integer setor = dto.idSetor();
        SectorModel sect = null;
        if (status.equals("OUT")){
            sect = sectorRepository.findById(dto.idSetor()).orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
            if (setor == null) {
                throw new IllegalArgumentException("Deve ser informado o setor de saida!");
            }
        }

        EquipmentModel equip = equipmentRepository.findById(dto.idEquipamento()).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        UserModel user = userRepository.findById(dto.idUsuario()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        MovementModel mov = new MovementModel();
        mov.setIdEquipment(equip);
        mov.setQuantity(dto.quantidade());
        mov.setnLykos(dto.nLykos());
        mov.setIdUser(user);
        mov.setType(status);
        mov.setIdSector(sect);
        mov.setDate(LocalDate.now());

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
