package com.techstockmaster.api.controllers;

import com.techstockmaster.api.domain.models.GeneralEquipmentModal;
import com.techstockmaster.api.services.GeneralEquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<GeneralEquipmentModal>> getAll() {
        List<GeneralEquipmentModal> usersList = service.findAll();
        if (!usersList.isEmpty()) {
            for (GeneralEquipmentModal user : usersList) {
                Integer id = user.getId();
                user.add(linkTo(methodOn(GeneralEquipmentController.class).getById(user.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(usersList);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<GeneralEquipmentModal> getById(Integer id) {
        GeneralEquipmentModal user = service.findById(id);
        if (user != null) {
            user.add(linkTo(methodOn(GeneralEquipmentController.class).getAll()).withRel("All General Equipment"));
            return ResponseEntity.status(200).body(user);
        } else {
            return ResponseEntity.status(404).build();
        }
    }


}
