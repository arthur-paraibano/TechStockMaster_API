package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.TagDto;
import com.techstockmaster.api.controllers.dtos.TagUpdateDto;
import com.techstockmaster.api.domain.models.TagModal;
import com.techstockmaster.api.services.TagService;
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
@RequestMapping(value = "/api/tag", produces = {"application/json"})
@Tag(name = "Tag's API", description = "API para operações com Tag's")
public class TagController {

    // Chamando a classe Service.
    @Autowired
    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    /*
     * Metodo busca todos os usuarios.
     */
    @GetMapping("/all")
    @Operation(summary = "Busca todos os Tag's", description = "Retorna uma lista de Tag's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Tag's retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum Tag encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<TagModal>> getAllTag() {
        List<TagModal> models = service.findAll();
        if (!models.isEmpty()) {
            for (TagModal list : models) {
                Integer id = list.getId();
                list.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }

    /*
     * Faz a busca pelo ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Busca Tag pelo ID", description = "Retorna um Tag específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<TagModal> getTagById(@PathVariable Integer id) {
        TagModal obj = service.findById(id);
        if (obj != null) {
            obj.add(linkTo(methodOn(TagController.class).getAllTag()).withRel("Tag List"));
            return ResponseEntity.status(HttpStatus.OK).body(obj);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*
     * Adiciona um nova tag, antes verifica se já existe um cadastrado.
     */
    @PostMapping("/addTag")
    @Operation(summary = "Adicionar um novo Tag", description = "Cria um novo Tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Os parâmetros informados estão incorretos com o esperado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Object> createTag(@Validated @RequestBody TagDto dto) {
        try {
            TagModal createdTag = service.create(dto);
            createdTag
                    .add(linkTo(methodOn(TagController.class).getTagById(createdTag.getId()))
                            .withSelfRel());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*
     * Deleta um tag pelo ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um Tag", description = "Deleta um Tag específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> deleteTag(@PathVariable Integer id) {
        try {
            TagModal model = service.delete(id);
            if (model != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Tag deletado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag não encontrado.");
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // Status 409 Conflict para erros de integridade
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }


    /*
     * Atualizar descrição da Tag
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTag(@PathVariable Integer id,
                                            @Validated @RequestBody TagUpdateDto dto) {
        try {
            TagModal update = service.updateTag(id, dto);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag não encontrado.");
            }
            update.add(linkTo(methodOn(UserController.class).getUserById(update.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
