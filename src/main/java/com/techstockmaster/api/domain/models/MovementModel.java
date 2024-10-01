package com.techstockmaster.api.domain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techstockmaster.api.domain.impl.TypeMovement;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Server
@Entity
@Table(name = "movimento")
public class MovementModel extends RepresentationModel<MovementModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "QUANTIDADE", nullable = false)
    private Double quantity;

    @Column(name = "N_LYKOS", length = 255, nullable = false)
    private String nLykos;

    @Column(name = "DATA", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "TYPE", length = 255, nullable = false)
    private TypeMovement type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CODEQUIP", nullable = false)
    @JsonIgnore
    private EquipmentModel idEquipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CODUSER", nullable = false)
    @JsonIgnore
    private UserModel idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CODSETOR", nullable = false)
    @JsonIgnore
    private SectorModel idSector;

}
