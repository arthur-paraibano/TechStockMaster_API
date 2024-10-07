package com.techstockmaster.api.domain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "movimento")
public class MovementModel extends RepresentationModel<MovementModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CODEQUIP", nullable = false)
    @JsonManagedReference
    private EquipmentModel idEquipment;

    @Setter
    @Column(name = "QUANTIDADE", nullable = false)
    private Double quantity;

    @Column(name = "N_LYKOS", length = 255, nullable = false)
    private String nLykos;

    @Setter
    @Column(name = "DATA", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CODUSER", nullable = false)
    @JsonIgnore
    private UserModel idUser;

    @Setter
    @Column(name = "TYPE", length = 255, nullable = false)
    private String type;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CODSETOR", nullable = false)
    @JsonManagedReference
    private SectorModel idSector;

    public void setnLykos(String nLykos) {
        this.nLykos = nLykos;
    }
}
