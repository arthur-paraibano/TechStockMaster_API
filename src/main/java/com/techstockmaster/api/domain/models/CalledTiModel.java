package com.techstockmaster.api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "chamados_ti")
public class CalledTiModel extends RepresentationModel<CalledTiModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_codlogin", nullable = false)
    @JsonIgnore
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_codsetor", nullable = false)
    @JsonIgnore
    private SectorModel sector;


    public CalledTiModel() {
    }
}