package com.techstockmaster.api.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "equipamento_geral")
public class GeneralEquipmentModel extends RepresentationModel<GeneralEquipmentModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_KERY")
    private Integer idKey;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ABREVIACAO_UM")
    private String abreviacaoUm;

    @Column(name = "DESCRICAO_UM")
    private String descricaoUm;
}
