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
@Table(name = "conserto")
public class RepairModel extends RepresentationModel<RepairModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TAG", nullable = false)
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_EQUIPE", nullable = false)
    @JsonManagedReference
    private EquipmentModel idEquipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SET", nullable = false)
    @JsonManagedReference
    private SectorModel idSector;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_TECNIC", nullable = false)
    @JsonManagedReference
    private UserModel idUser;

    @Column(name = "DATA", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "STATUS", nullable = false)
    private String status;

  //  private String sequencia;
}
