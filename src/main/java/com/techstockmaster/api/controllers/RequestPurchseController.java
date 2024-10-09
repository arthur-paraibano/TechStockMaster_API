package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.RequestPurchseDto;
import com.techstockmaster.api.controllers.dtos.RequestPurchseUpdateDto;
import com.techstockmaster.api.domain.models.RequestPurchseModel;
import com.techstockmaster.api.domain.models.SupervisorModel;
import com.techstockmaster.api.services.RequestPurchseService;
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
@RequestMapping(value = "/api/Solicitar compra", produces = {"application/json"})
@Tag(name = "Solicitar compra API", description = "API para operações relacionadas a Solicitar compra")
public class RequestPurchseController {

    @Autowired
    private final RequestPurchseService service;

    public RequestPurchseController(RequestPurchseService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos as Solicitações de compras", description = "Retorna uma lista de todos as Solicitações de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de solicitações de compras retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum selicitação encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<RequestPurchseModel>> getAll() {
        List<RequestPurchseModel> models = service.findAll();
        if (!models.isEmpty()) {
            for (RequestPurchseModel list : models) {
                Integer id = list.getId();
                list.add(linkTo(methodOn(RequestPurchseController.class).getById(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(models);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma solicitação de compra por ID", description = "Retorna uma solicitação de compra específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação de compra encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Solicitação de compra não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<RequestPurchseModel> getById(@PathVariable Integer id) {
        RequestPurchseModel mov = service.findById(id);
        if (mov != null) {
            mov.add(linkTo(methodOn(RequestPurchseController.class).getAll()).withRel("Solicitação de compra List"));
            return ResponseEntity.status(HttpStatus.OK).body(mov);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo solicitação de compra", description = "Cria um novo solicitação de compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitação criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> add(@Validated @RequestBody RequestPurchseDto dto) {
        try {
            RequestPurchseModel newRepair = service.create(dto);
            newRepair.add(linkTo(methodOn(RequestPurchseController.class).getAll()).withRel("All Consertos"));
            return ResponseEntity.status(HttpStatus.CREATED).body(newRepair);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Status de Solicitações", description = "Atualiza um status existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no solicitação")
    })
    public ResponseEntity<Object> updateSupervisor(@PathVariable Integer id, @Validated @RequestBody RequestPurchseUpdateDto dto) {
        try {
            RequestPurchseModel update = service.updateStatus(id, dto);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitação de compra não encontrada.");
            }
            update.add(linkTo(methodOn(SupervisorController.class).getSupervisorById(update.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
