package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.FeedbackDto;
import com.techstockmaster.api.domain.models.FeedbackModel;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.FeedbackRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private final FeedbackRepository feedbackRepository;

    @Autowired
    private final UserRepository userRepository;

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
        String status = dto.relevancia().toUpperCase();
        if (!status.matches("ALTO|MÉDIO|BAIXO")) {
            throw new IllegalArgumentException("Relevância deve ser ALTO, MÉDIO ou BAIXO");
        }

        FeedbackModel feedback = new FeedbackModel();
        feedback.setDate(LocalDate.now());
        feedback.setDescricao(dto.descricaoMensagem());
        feedback.setStatus(dto.relevancia().toUpperCase());

        // Buscar usuário pelo ID
        UserModel user = userRepository.findById(dto.idUser())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        feedback.setUser(user);

        return feedbackRepository.save(feedback);
    }

    public FeedbackModel delete(Integer id) {
        return null;
    }

    public FeedbackModel update(Integer integer, FeedbackDto entity) {
        return null;
    }
}