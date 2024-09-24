package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.FeedbackDto;
import com.techstockmaster.api.domain.models.FeedbackModel;
import com.techstockmaster.api.services.FeedbackService;
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
@RequestMapping(value = "/api/feedback", produces = {"application/json"})
@Tag(name = "Feedback API", description = "API para operações relacionadas ao Feedback")
public class FeedbackController {

    // Chamando a classe Service.
    @Autowired
    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<FeedbackModel>> getAll() {
        List<FeedbackModel> usersList = service.findAll();
        if (!usersList.isEmpty()) {
            for (FeedbackModel user : usersList) {
                Integer id = user.getId();
                user.add(linkTo(methodOn(FeedbackController.class).getById(user.getId())).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(usersList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuários por ID", description = "Retorna um usuário específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FeedbackModel> getById(@PathVariable Integer id) {
        FeedbackModel user = service.findById(id);
        if (user != null) {
            user.add(linkTo(methodOn(FeedbackController.class).getAll()).withRel("All Feedbacks"));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo feedback", description = "Cria um novo feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> add(@Validated @RequestBody FeedbackDto dto) {
        try {
            FeedbackModel newFeedback = service.create(dto);
            newFeedback.add(linkTo(methodOn(FeedbackController.class).getAll()).withRel("All Feedbacks"));
            return ResponseEntity.status(HttpStatus.CREATED).body(newFeedback);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}