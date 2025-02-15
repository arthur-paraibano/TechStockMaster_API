package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.controllers.dtos.SectorLocacaoDto;
import com.techstockmaster.api.controllers.dtos.SectorSupervisorDto;
import com.techstockmaster.api.domain.models.SectorModel;
import com.techstockmaster.api.services.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
 *  Classe responsável por fazer a ligação com o Usuário pelas requisições e o Serviço que faz as buscas
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/sector", produces = {"application/json"})
@Tag(name = "Sector API", description = "API para operações relacionadas ao Setores")
public class SectorController {

    @Autowired
    private final SectorService service;

    @GetMapping("/all")
    @Operation(summary = "Obter todos os Setores", description = "Retorna uma lista de todos os Setores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setores encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum Setor encontrado")
    })
    public ResponseEntity<List<SectorModel>> getAll() {
        List<SectorModel> usersList = service.findAll();
        if (!usersList.isEmpty()) {
            for (SectorModel user : usersList) {
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
    public ResponseEntity<SectorModel> getById(@PathVariable Integer id) {
        SectorModel user = service.findById(id);
        if (user != null) {
            user.add(linkTo(methodOn(SectorController.class).getAll()).withRel("All Feedbacks"));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo Setor", description = "Cria um novo Setor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Setor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Object> add(@Validated @RequestBody SectorDto dto) {
        try {
            SectorModel modal = service.create(dto);
            modal.add(linkTo(methodOn(FeedbackController.class).getAll()).withRel("All Feedbacks"));
            return ResponseEntity.status(HttpStatus.CREATED).body(modal);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/supervisor")
    @Operation(summary = "Atualizar Supervisor", description = "Atualiza a responsável pelo setor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> updateSuperior(@PathVariable Integer id, @RequestBody SectorSupervisorDto dto) {
        try {
            SectorModel update = service.updateSupervisor(id, dto);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sector não encontrado.");
            }
            update.add(linkTo(methodOn(SectorController.class).getById(update.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/locacao")
    @Operation(summary = "Atualizar locação", description = "Atualiza a locação do setor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locação atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Locação não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> updateLocacao(@PathVariable Integer id, @RequestBody SectorLocacaoDto dto) {
        try {
            SectorModel update = service.updateLocacao(id, dto);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sector não encontrado.");
            }
            update.add(linkTo(methodOn(SectorController.class).getById(update.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}