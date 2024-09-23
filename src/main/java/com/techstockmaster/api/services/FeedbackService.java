package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.FeedbackDto;
import com.techstockmaster.api.domain.models.FeedbackModel;

public interface FeedbackService extends CrudService<FeedbackModel, Integer, FeedbackDto> {
}
