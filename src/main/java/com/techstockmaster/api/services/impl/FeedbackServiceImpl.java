package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.FeedbackDto;
import com.techstockmaster.api.domain.models.FeedbackModel;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.FeedbackRepository;
import com.techstockmaster.api.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<FeedbackModel> findAll() {
        return feedbackRepository.findAll();
    }

    public FeedbackModel findById(Integer id) {
        Optional<FeedbackModel> user = feedbackRepository.findById(id);
        return user.orElse(null);
    }

    /*
     * Cria uma nova tag, antes verifica se já existe um cadastrado
     */
    public FeedbackModel create(FeedbackDto dto) {
        FeedbackModel tag = new FeedbackModel();
        tag.setDate(LocalDate.now());
        tag.getDescricao().toUpperCase();
        tag.setStatus(dto.relevancia().toUpperCase());
        tag.setUser(new UserModel(dto.idUser()));
        return feedbackRepository.save(tag);
    }

    // Metodo para excluir usuário passando o ID dele
    public FeedbackModel delete(Integer id) {
        return null;
    }

    public FeedbackModel update(Integer integer, FeedbackDto entity) {
        return null;
    }
}
