package com.techstockmaster.api.controllers;

import com.techstockmaster.api.controllers.dtos.UserBlockedDto;
import com.techstockmaster.api.controllers.dtos.UserDto;
import com.techstockmaster.api.controllers.dtos.UserPasswordDto;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.services.UserService;
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
@RequestMapping(value = "/api/users", produces = {"application/json"})
@Tag(name = "Usuários API", description = "API para operações com usuários")
public class UserController {

    // Chamando a classe Service.
    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> usersList = userService.findAll();
        if (!usersList.isEmpty()) {
            for (UserModel user : usersList) {
                Integer id = user.getId();
                user.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
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
    public ResponseEntity<UserModel> getUserById(@PathVariable Integer id) {
        UserModel user = userService.findById(id);
        if (user != null) {
            user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("Users List"));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add-user")
    @Operation(summary = "Adicionar um novo usuário", description = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> createUser(@Validated @RequestBody UserDto userDto) {
        try {
            UserModel createdUser = userService.create(userDto);
            createdUser.add(linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/block")
    @Operation(summary = "Bloquear usuário", description = "Bloquea um usuário baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário bloqueado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<String> blockedUser(@PathVariable Integer id, UserBlockedDto dto) {
        try {
            UserModel user = userService.blockedUserById(id, dto);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não bloqueado.");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Usuário bloqueado com sucesso.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @Validated @RequestBody UserDto userDto) {
        try {
            UserModel update = userService.update(id, userDto);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            update.add(linkTo(methodOn(UserController.class).getUserById(update.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/reset-password/{id}")
    @Operation(summary = "Alterar senha do usuário", description = "Atualiza a senha de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> updatePasswordUser(@PathVariable Integer id,
                                                     @Validated @RequestBody UserPasswordDto resetPasswordDto) {
        UserModel update = userService.updatePasswordUser(id, resetPasswordDto);
        if (update == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        update.add(linkTo(methodOn(UserController.class).getUserById(update.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }
}