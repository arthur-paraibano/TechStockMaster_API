package com.techstockmaster.api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "equipamento")
public class EquipmentModel extends RepresentationModel<EquipmentModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CODIGO", length = 255, nullable = false)
    private String code;

    @Column(name = "EQUIPAMENTO", length = 255, nullable = false)
    private String equipment;

    @Column(name = "UND", length = 255, nullable = false)
    private String und;

    @Column(name = "SALDO_ATUAL", nullable = false)
    private Double currentBalance;

    @Column(name = "CONSERTO", length = 255, nullable = false)
    private Integer repair;

    @Column(name = "EQUIP_CHECK", length = 255, nullable = false)
    private Integer equipCheck;

    @Column(name = "DESCRICAO", length = 255, nullable = false)
    private String description;

    @Column(name = "STATUS", length = 255, nullable = false)
    private String status;

    @Column(name = "TAG_SEQ", nullable = false)
    private Integer tagSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CODSETOR", nullable = false)
    @JsonIgnore
    private SectorModel idSector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TAG", nullable = false)
    @JsonIgnore
    private TagModel tag;
}
