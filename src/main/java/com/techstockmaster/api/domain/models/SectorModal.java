package com.techstockmaster.api.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "setor")
public class SectorModal extends RepresentationModel<FeedbackModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 255)
    private String name;

    @Column(name = "LOCACAO", length = 255)
    private String retal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SUPERVISOR", nullable = false)
    @JsonManagedReference
    private SupervisorModel supervisor;
}
