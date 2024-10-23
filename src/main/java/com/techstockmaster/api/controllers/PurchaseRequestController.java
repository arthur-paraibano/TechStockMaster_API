package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.PurchaseRequestDto;
import com.techstockmaster.api.domain.models.PurchaseRequestModel;
import com.techstockmaster.api.services.PurchaseRequestService;
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
@RequestMapping(value = "/api/consertos", produces = {"application/json"})
@Tag(name = "Conserto API", description = "API para operações relacionadas ao Conserto de equipamento")
public class PurchaseRequestController {

    private final PurchaseRequestService service;

    @Autowired
    public PurchaseRequestController(PurchaseRequestService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os consertos", description = "Retorna uma lista de todos os consertos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consertos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum conserto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<PurchaseRequestModel>> getAll() {
        List<PurchaseRequestModel> models = service.findAll();
        if (!models.isEmpty()) {
         //   models.forEach(model -> model.add(linkTo(methodOn(PurchaseRequestController.class).getById(model.getId())).withSelfRel()));
            return ResponseEntity.ok(models);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conserto por ID", description = "Retorna um conserto específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conserto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conserto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<PurchaseRequestModel> getById(@PathVariable Integer id) {
        PurchaseRequestModel repairRequest = service.findById(id);
        if (repairRequest != null) {
            repairRequest.add(linkTo(methodOn(PurchaseRequestController.class).getAll()).withRel("Conserto List"));
            return ResponseEntity.ok(repairRequest);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @PostMapping("/add")
//    @Operation(summary = "Adicionar um novo conserto", description = "Cria um novo Conserto")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Conserto criado com sucesso"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
//    })
//    public ResponseEntity<Object> add(@Validated @RequestBody PurchaseRequestDto dto) {
//        try {
//            PurchaseRequestModel newRepair = service.create(dto);
//            newRepair.add(linkTo(methodOn(PurchaseRequestController.class).getAll()).withRel("All Consertos"));
//            return ResponseEntity.status(HttpStatus.CREATED).body(newRepair);
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar conserto: " + e.getMessage());
//        }
//    }
}