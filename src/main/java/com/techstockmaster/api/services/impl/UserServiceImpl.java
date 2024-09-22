package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.UserDto;
import com.techstockmaster.api.controllers.dtos.UserPasswordDto;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.UserService;
import com.techstockmaster.api.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Aqui estão todos os métodos com conexões com o banco de dados e algumas validações
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository; // Injeção da classe UserRepository

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Puxa tudo que existir na tabela informada no Modal
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    // Puxa apenas o elemento que corresponder ao ID
    public UserModel findById(Integer id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.orElse(null);
    }

    // Cria um novo usuário no banco de dados, por padrão os métodos
    // “user.setTemporaryPassword(true);” Diz que a senha cadastrada é uma senha
    // temporária. O “user.setBlocked("0");” O usuário cadastrado não é pra tar
    // bloqueado o acesso
    public UserModel create(UserDto userDto) {
        if (userRepository.existsByGmail(userDto.gmail())) {
            throw new DataIntegrityViolationException("Email já existe!");
        }
        if (userRepository.existsByUsername(userDto.username())) {
            throw new DataIntegrityViolationException("Username já existe!");
        }
        UserModel user = new UserModel();

        user.setFullName(userDto.fullName().toUpperCase());
        user.setGmail(userDto.gmail().toUpperCase());
        user.setUsername(userDto.username().toUpperCase());
        String senha = userDto.password().toUpperCase();
        user.setPassword(Encrypt.encriptografar(senha));
        user.setFullAccess(userDto.fullAccess().toUpperCase().toUpperCase());
        user.setUserType(userDto.userType().toUpperCase());
        user.setTemporaryPassword(true);
        user.setBlocked("0");
        return userRepository.save(user);
    }

    // Metodo para excluir usuário passando o ID dele
    public UserModel delete(Integer id) {
        Optional<UserModel> user = userRepository.findById(id);
        userRepository.delete(user.get());
        return user.orElse(null);
    }

    // Metodo para fazer alterações passando o ID do usuário
    public UserModel update(Integer id, UserDto userDto) {
        Optional<UserModel> existingUserOpt = userRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            return null;
        }

        UserModel existingUser = existingUserOpt.get();

        if (userRepository.existsByGmail(userDto.gmail()) && !existingUser.getGmail().equals(userDto.gmail())) {
            throw new DataIntegrityViolationException("Email já existe!");
        }

        if (userRepository.existsByUsername(userDto.username())
                && !existingUser.getUsername().equals(userDto.username())) {
            throw new DataIntegrityViolationException("Username já existe!");
        }

        existingUser.setFullName(userDto.fullName().toUpperCase());
        existingUser.setGmail(userDto.gmail().toUpperCase());
        existingUser.setUsername(userDto.username().toUpperCase());
        String senha = userDto.password().toUpperCase();
        existingUser.setPassword(Encrypt.encriptografar(senha));
        existingUser.setFullAccess(userDto.fullAccess().toUpperCase().toUpperCase());
        existingUser.setUserType(userDto.userType().toUpperCase());
        existingUser.setTemporaryPassword(true);
        existingUser.setBlocked("0");

        return userRepository.save(existingUser);
    }

    // Metodo para redefinir senha do Usuario no primeiro login.
    public UserModel updatePasswordUser(Integer id, UserPasswordDto resetPasswordDto) {

        System.out.println(resetPasswordDto);

        Optional<UserModel> existingUserOpt = userRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            return null;
        }

        UserModel existingUser = existingUserOpt.get();

        String senha = resetPasswordDto.password().toUpperCase();
        existingUser.setPassword(Encrypt.encriptografar(senha));
        existingUser.setTemporaryPassword(false);

        return userRepository.save(existingUser);

    }
}