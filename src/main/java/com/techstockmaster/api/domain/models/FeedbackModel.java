package com.techstockmaster.api.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class FeedbackModel extends RepresentationModel<FeedbackModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USERID", nullable = false)
    @JsonManagedReference
    private UserModel user;
}