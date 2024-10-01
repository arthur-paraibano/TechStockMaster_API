package com.techstockmaster.api.controllers;

import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.services.MovementService;
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
@RequestMapping(value = "/api/movement", produces = {"application/json"})
@Tag(name = "Movement API", description = "API para operações relacionadas ao Movimentos")
public class MovementController {

    @Autowired
    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os movimentos", description = "Retorna uma lista de todos os movimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimentos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum movimento encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<MovementModel>> getAllMovement() {
        List<MovementModel> models = service.findAll();
        if (!models.isEmpty()) {
            for (MovementModel list : models) {
                Integer id = list.getId();
                list.add(linkTo(methodOn(MovementController.class).getMovementById(id)).withSelfRel());
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
    private ResponseEntity<MovementModel> getMovementById(Integer id) {
        MovementModel model = service.findById(id);
        if (model != null) {
            model.add(linkTo(methodOn(MovementController.class).getAllMovement()).withRel("Movement List"));
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
