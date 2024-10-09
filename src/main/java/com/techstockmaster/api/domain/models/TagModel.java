package com.techstockmaster.api.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = "abrev_tag"),
        @UniqueConstraint(columnNames = "desc_tag")
})
public class TagModel extends RepresentationModel<TagModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotBlank(message = "A abreviação da tag não pode estar em branco")
    @Column(name = "abrev_tag", nullable = false)
    private String abrevTag;

    @NotBlank(message = "A descrição da tag não pode estar em branco")
    @Column(name = "desc_tag", nullable = false)
    private String descTag;

    @NotNull(message = "A data não pode ser nula")
    @Column(name = "DATA", nullable = false)
    private LocalDate date;
}
