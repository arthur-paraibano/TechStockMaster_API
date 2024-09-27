package com.techstockmaster.api.controllers;

import com.techstockmaster.api.domain.models.GeneralEquipmentModal;
import com.techstockmaster.api.services.GeneralEquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/generalEquipment", produces = {"application/json"})
@Tag(name = "General Equipment API", description = "API para operações relacionadas ao Equipamento Geral")
public class GeneralEquipmentController {

    @Autowired
    private final GeneralEquipmentService service;

    public GeneralEquipmentController(GeneralEquipmentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os equipamentos", description = "Retorna uma lista de todos os equipamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipamentos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum equipamento encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<GeneralEquipmentModal>> getAll() {
        List<GeneralEquipmentModal> equipmentList = service.findAll();
        if (!equipmentList.isEmpty()) {
            for (GeneralEquipmentModal equipment : equipmentList) {
                equipment.add(linkTo(methodOn(GeneralEquipmentController.class).getById(equipment.getId())).withSelfRel());
            }
            return ResponseEntity.ok().body(equipmentList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<GeneralEquipmentModal> getById(Integer id) {
        try {
            GeneralEquipmentModal equipment = service.findById(id);
            if (equipment != null) {
                equipment.add(linkTo(methodOn(GeneralEquipmentController.class).getAll()).withRel("All General Equipment"));
                return ResponseEntity.status(HttpStatus.OK).body(equipment);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/add")
    @Operation(summary = "Executar a integração de equipamentos", description = "Executa a integração entre a base Lykos e o banco de dados local")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor durante a integração")
    })
    public ResponseEntity<String> add() {
        try {
            int rowsProcessed = service.executeIntegration();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Integração concluída com sucesso. Equipamentos adicionados: " + rowsProcessed);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro durante a integração: " + e.getMessage());
        }
    }
}