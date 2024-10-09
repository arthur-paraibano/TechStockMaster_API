package com.techstockmaster.api.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "solicitacao_compra")
public class RequestPurchseModel extends RepresentationModel<RequestPurchseModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CODEQUIP", nullable = false)
    @JsonManagedReference
    private EquipmentModel idEquipment;

    @Column(name = "QUANTIDADE", nullable = false)
    private Double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CODSETOR", nullable = false)
    @JsonManagedReference
    private SectorModel idSector;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CODTECNICO", nullable = false)
    @JsonManagedReference
    private UserModel idUser;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "DATA", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
