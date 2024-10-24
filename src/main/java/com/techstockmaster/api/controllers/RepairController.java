package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.RepairDto;
import com.techstockmaster.api.controllers.dtos.RepairStatusDto;
import com.techstockmaster.api.domain.models.RepairModel;
import com.techstockmaster.api.services.RepairService;
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
public class RepairController {

    @Autowired
    private final RepairService service;

    public RepairController(RepairService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os consertos", description = "Retorna uma lista de todos os consertos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consertos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum conserto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<RepairModel>> getAll() {
        List<RepairModel> models = service.findAll();
        if (!models.isEmpty()) {
            for (RepairModel list : models) {
                Integer id = list.getId();
                list.add(linkTo(methodOn(RepairController.class).getById(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(models);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conserto por ID", description = "Retorna um conserto específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conserto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conserto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<RepairModel> getById(@PathVariable Integer id) {
        RepairModel mov = service.findById(id);
        if (mov != null) {
            mov.add(linkTo(methodOn(RepairController.class).getAll()).withRel("Conserto List"));
            return ResponseEntity.status(HttpStatus.OK).body(mov);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Adicionar um novo conserto", description = "Cria um novo Conserto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conserto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> add(@Validated @RequestBody RepairDto dto) {
        try {
            RepairModel newRepair = service.create(dto);
            newRepair.add(linkTo(methodOn(RepairController.class).getAll()).withRel("All Consertos"));
            return ResponseEntity.status(HttpStatus.CREATED).body(newRepair);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualizar Status", description = "Atualiza o status da equipamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> updateLocacao(@PathVariable Integer id, @RequestBody RepairStatusDto dto) {
        try {
            RepairModel repairModel = service.updateStatus(id, dto);
            repairModel.add(linkTo(methodOn(RepairController.class).getById(repairModel.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(repairModel);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
