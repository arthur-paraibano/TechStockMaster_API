package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.TagDto;
import com.techstockmaster.api.controllers.dtos.TagUpdateDto;
import com.techstockmaster.api.domain.models.TagModal;
import com.techstockmaster.api.domain.repositories.TagRepository;
import com.techstockmaster.api.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 * Aqui estão todos os métodos com conexões com o banco de dados e algumas validações
 */
@Service
public class TagServiceImpl implements TagService {

    // Injeção da classe UserRepository
    @Autowired
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // Puxa tudo que existir na tabela informada no Modal
    public List<TagModal> findAll() {
        return tagRepository.findAll();
    }

    // Puxa apenas o elemento que corresponder ao ID
    public TagModal findById(Integer id) {
        Optional<TagModal> user = tagRepository.findById(id);
        return user.orElse(null);
    }

    /*
     * Cria uma nova tag, antes verifica se já existe um cadastrado
     */
    public TagModal create(TagDto dto) {
        if (tagRepository.existsByAbrevTag(dto.abreviacao())) {
            throw new DataIntegrityViolationException("Abreviação já existe");
        }
        Long count = tagRepository.countByDescTag(dto.descircao());
        if (count > 0) {
            throw new DataIntegrityViolationException("Descrição já existe");
        }
        TagModal tag = new TagModal();
        tag.setAbrevTag(dto.abreviacao().toUpperCase());
        tag.setDescTag(dto.descircao().toUpperCase());
        tag.setDate(LocalDate.now());
        return tagRepository.save(tag);
    }

    // Metodo para excluir usuário passando o ID dele
    public TagModal delete(Integer id) {
        Optional<TagModal> user = tagRepository.findById(id);
        Long count = tagRepository.existsTagInEquipamento(id);
        if (count > 0) {
            throw new DataIntegrityViolationException("Tag está em uso");
        }
        tagRepository.delete(user.get());
        return user.orElse(null);
    }

    @Override
    public TagModal update(Integer integer, TagDto entity) {
        return null;
    }

    // Metodo para fazer alterações apenas na descrição da tag
    public TagModal updateTag(Integer id, TagUpdateDto dto) {
        Optional<TagModal> existingTagOpt = tagRepository.findById(id);
        if (!existingTagOpt.isPresent()) {
            return null;
        }
        TagModal existinTag = existingTagOpt.get();

        existinTag.setDescTag(dto.descircao().toUpperCase());
        existinTag.setDate(LocalDate.now());
        return tagRepository.save(existinTag);
    }
}