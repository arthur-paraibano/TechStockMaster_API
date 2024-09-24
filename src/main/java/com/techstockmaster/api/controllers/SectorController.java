package com.techstockmaster.api.controllers;

import com.techstockmaster.api.domain.models.SectorModal;
import com.techstockmaster.api.services.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
 *  Classe responsável por fazer a ligação com o Usuário pelas requisições e o Serviço que faz as buscas
 */
@RestController
@RequestMapping(value = "/api/sector", produces = {"application/json"})
@Tag(name = "Sector API", description = "API para operações relacionadas ao Setores")
public class SectorController {

    @Autowired
    private final SectorService service;

    public SectorController(SectorService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Obter todos os Setores", description = "Retorna uma lista de todos os Setores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setores encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum Setor encontrado")
    })
    public ResponseEntity<List<SectorModal>> getAll() {
        List<SectorModal> usersList = service.findAll();
        if (!usersList.isEmpty()) {
            for (SectorModal user : usersList) {
                Integer id = user.getId();
                user.add(linkTo(methodOn(SectorController.class).getById(user.getId())).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(usersList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um Setor pelo ID", description = "Retorna um Setor específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setor encontrado"),
            @ApiResponse(responseCode = "404", description = "Setor não encontrado")
    })
    public ResponseEntity<SectorModal> getById(@PathVariable Integer id) {
        SectorModal user = service.findById(id);
        if (user != null) {
            user.add(linkTo(methodOn(SectorController.class).getAll()).withRel("All Feedbacks"));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}