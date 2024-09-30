package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.EquipmentDto;
import com.techstockmaster.api.domain.models.EquipmentModal;
import com.techstockmaster.api.services.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/equipamentos", produces = {"application/json"})
@Tag(name = "Equipamentos API", description = "API para operações relacionadas a equipamentos")
public class EquipmentController {

    @Autowired
    private final EquipmentService service;

    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os equipamentos", description = "Retorna uma lista de todos os equipamentos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamentos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum equipamento encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<EquipmentModal>> getAll() {
        List<EquipmentModal> equipment = service.findAll();
        if (!equipment.isEmpty()) {
            for (EquipmentModal equipmentos : equipment) {
                Integer id = equipmentos.getId();
                equipmentos.add(linkTo(methodOn(EquipmentController.class).getById(equipmentos.getId())).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(equipment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EquipmentModal> getById(@PathVariable Integer id) {
        EquipmentModal equipment = service.findById(id);
        if (equipment != null) {
            equipment.add(linkTo(methodOn(EquipmentController.class).getAll()).withRel("All Equipment"));
            return ResponseEntity.status(HttpStatus.OK).body(equipment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo Material/Equipamento", description = "Cria um novo Material/Equipamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material/Equipamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> add(@Validated @RequestBody EquipmentDto dto) {
        try {
            EquipmentModal modal = service.create(dto);
            modal.add(linkTo(methodOn(EquipmentController.class).getAll()).withRel("All Material/Equipamento"));
            return ResponseEntity.status(HttpStatus.CREATED).body(modal);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
