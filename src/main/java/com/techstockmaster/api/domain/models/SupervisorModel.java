package com.techstockmaster.api.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "supervisor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SupervisorModel extends RepresentationModel<SupervisorModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOME")
    @Nonnull
    private String name;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<SectorModel> sectorModels;
}
