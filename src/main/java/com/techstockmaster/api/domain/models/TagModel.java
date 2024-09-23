package com.techstockmaster.api.domain.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = "abrev_tag"),
        @UniqueConstraint(columnNames = "descTag")
})
public class TagModel extends RepresentationModel<TagModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "abrev_tag")
    @Setter
    @Nonnull
    @NotBlank(message = "A abreviação não deve estar em branco")
    private String abrevTag;

    @Column(name = "descTag")
    @Setter
    @Nonnull
    @NotBlank(message = "A descrição não deve estar em branco")
    private String descTag;

    @Column(name = "DATA")
    @Setter
    @Nonnull
    @NotNull(message = "A data não pode ser nula")
    private LocalDate date;
}