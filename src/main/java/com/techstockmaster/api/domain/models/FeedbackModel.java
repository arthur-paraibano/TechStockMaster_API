package com.techstockmaster.api.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "feedback", uniqueConstraints = {
        // A anotação @UniqueConstraint é usada para garantir que uma ou mais colunas tenham valores únicos na tabela
        @UniqueConstraint(columnNames = "id")
})
public class FeedbackModel {

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
    @JoinColumn(name = "fk_userid", nullable = false)
    private UserModel user;
}