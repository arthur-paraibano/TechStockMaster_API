package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.CalledTiDto;
import com.techstockmaster.api.domain.models.CalledTiModel;
import com.techstockmaster.api.services.CalledTIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/chamados", produces = {"application/json"})
@Tag(name = "Chamados API", description = "API para operações relacionadas a chamados TI")
public class CalledTiController {

    @Autowired
    private final CalledTIService service;

    public CalledTiController(CalledTIService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os chamados", description = "Retorna uma lista de todos os chamados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum chamado encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<CalledTiModel>> getAll() {
        List<CalledTiModel> chamado = service.findAll();
        if (!chamado.isEmpty()) {
            for (CalledTiModel chamados : chamado) {
                Integer id = chamados.getId();
                chamados.add(linkTo(methodOn(CalledTiController.class).getById(chamados.getId())).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(chamado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar chamado por ID", description = "Retorna um chamado específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CalledTiModel> getById(@PathVariable Integer id) {
        CalledTiModel chamado = service.findById(id);
        if (chamado != null) {
            chamado.add(linkTo(methodOn(CalledTiController.class).getAll()).withRel("All Chamados"));
            return ResponseEntity.status(HttpStatus.OK).body(chamado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Criar um novo chamado", description = "Cria um novo chamado com base nos dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CalledTiModel> create(@RequestBody CalledTiDto chamadoDto) {
        CalledTiModel chamado = service.create(chamadoDto);
        if (chamado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(chamado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Atualizar um chamado existente", description = "Atualiza um chamado existente com base nos dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CalledTiModel> update(@PathVariable Integer id, @RequestBody CalledTiDto chamadoDto) {
        CalledTiModel updated = service.update(id, chamadoDto);
        if (updated != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}