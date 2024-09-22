package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.UserDto;
import com.techstockmaster.api.controllers.dtos.UserPasswordDto;
import com.techstockmaster.api.domain.models.UserModel;

import java.util.List;

public interface UserService extends CrudService<UserModel, Integer, UserDto> {
    UserModel updatePasswordUser(Integer id, UserPasswordDto resetPasswordDto);
}
