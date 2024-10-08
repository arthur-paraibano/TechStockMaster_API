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

@Entity
@Setter
@Getter
@Table(name = "tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = "abrevtag"),
        @UniqueConstraint(columnNames = "desctag")
})
public class TagModel extends RepresentationModel<TagModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "abrevtag")
    private String abrevTag;

    @Column(name = "desctag")
    private String descTag;

    @Column(name = "DATA")
    private LocalDate date;
}