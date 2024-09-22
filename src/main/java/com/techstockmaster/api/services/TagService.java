package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.TagDto;
import com.techstockmaster.api.controllers.dtos.TagUpdateDto;
import com.techstockmaster.api.controllers.dtos.UserDto;
import com.techstockmaster.api.domain.models.TagModal;
import com.techstockmaster.api.domain.models.UserModel;

import java.util.List;

public interface TagService extends CrudService<TagModal, Integer, TagDto>{
    TagModal updateTag(Integer id, TagUpdateDto dto);
}
