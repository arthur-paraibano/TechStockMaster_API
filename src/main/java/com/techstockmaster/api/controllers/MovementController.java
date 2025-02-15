package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.MovementDto;
import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.services.MovementService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/movement", produces = {"application/json"})
@Tag(name = "Movement API", description = "API para operações relacionadas ao Movimentos")
public class MovementController {

    @Autowired
    private final MovementService service;


    @GetMapping("/all")
    @Operation(summary = "Listar todos os movimentos", description = "Retorna uma lista de todos os movimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimentos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum movimento encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<MovementModel>> getAll() {
        List<MovementModel> models = service.findAll();
        if (!models.isEmpty()) {
            for (MovementModel list : models) {
                Integer id = list.getId();
                list.add(linkTo(methodOn(MovementController.class).getById(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(models);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar movimento por ID", description = "Retorna um movimento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Movimento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<MovementModel> getById(@PathVariable Integer id) {
        MovementModel mov = service.findById(id);
        if (mov != null) {
            mov.add(linkTo(methodOn(MovementController.class).getAll()).withRel("Movement List"));
            return ResponseEntity.status(HttpStatus.OK).body(mov);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo movimento", description = "Cria um novo movimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> add(@Validated @RequestBody MovementDto dto) {
        try {
            MovementModel newMovement = service.create(dto);
            newMovement.add(linkTo(methodOn(MovementController.class).getAll()).withRel("All Movement"));
            return ResponseEntity.status(HttpStatus.CREATED).body(newMovement);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
