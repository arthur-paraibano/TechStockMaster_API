package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.SupervisorDto;
import com.techstockmaster.api.domain.models.SupervisorModel;
import com.techstockmaster.api.services.SupervisorService;
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

/*
 *  Classe responsável por fazer a ligação com o Usuário pelas requisições e o Serviço que faz as buscas
 */
@RestController
@RequestMapping(value = "/api/supervisor", produces = {"application/json"})
@Tag(name = "Supervisor API", description = "API para operações com supervisores")
public class SupervisorController {

    // Chamando a classe Service.
    @Autowired
    private final SupervisorService service;

    public SupervisorController(SupervisorService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os supervisores", description = "Retorna uma lista de todos os supervisores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de supervisores retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum supervisor encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<SupervisorModel>> getAllSupervisor() {
        List<SupervisorModel> models = service.findAll();
        if (!models.isEmpty()) {
            for (SupervisorModel list : models) {
                Integer id = list.getId();
                list.add(linkTo(methodOn(SupervisorController.class).getSupervisorById(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(models);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar supervisor por ID", description = "Retorna um supervisor específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<SupervisorModel> getSupervisorById(@PathVariable Integer id) {
        SupervisorModel obj = service.findById(id);
        if (obj != null) {
            obj.add(linkTo(methodOn(SupervisorController.class).getAllSupervisor()).withRel("Supervisor List"));
            return ResponseEntity.status(HttpStatus.OK).body(obj);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo supervisor", description = "Cria um novo supervisor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supervisor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> createSupervisor(@Validated @RequestBody SupervisorDto dto) {
        try {
            SupervisorModel createdSupervisor = service.create(dto);
            createdSupervisor.add(linkTo(methodOn(SupervisorController.class).getSupervisorById(createdSupervisor.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSupervisor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar supervisor", description = "Remove um supervisor baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        SupervisorModel model = service.delete(id);
        if (model != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Supervisor deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor não deletado.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar supervisor", description = "Atualiza um supervisor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supervisor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Supervisor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> updateSupervisor(@PathVariable Integer id,
                                                   @Validated @RequestBody SupervisorDto dto) {
        try {
            SupervisorModel update = service.update(id, dto);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor não encontrado.");
            }
            update.add(linkTo(methodOn(SupervisorController.class).getSupervisorById(update.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}