package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.TagDto;
import com.techstockmaster.api.controllers.dtos.TagUpdateDto;
import com.techstockmaster.api.domain.models.TagModel;

public interface TagService extends CrudService<TagModel, Integer, TagDto>{
    TagModel updateTag(Integer id, TagUpdateDto dto);
}
