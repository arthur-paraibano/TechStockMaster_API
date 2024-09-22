package com.techstockmaster.api.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "usernames", uniqueConstraints = { // A anotação @UniqueConstraint é usada para garantir que uma ou mais
        // colunas tenham valores únicos na tabela
        @UniqueConstraint(columnNames = "gmail"), @UniqueConstraint(columnNames = "username")// Garantir que colunas como gmail e username tenham valores únicos é
        // importante em muitos cenários
})
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Setter
    @Column(name = "full_name", length = 200, nullable = false)
    private String fullName;

    @Setter
    @Column(name = "gmail", nullable = false)
    private String gmail;

    @Setter
    @Column(name = "username", nullable = false)
    private String username;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "full_access", nullable = false)
    private String fullAccess;

    @Setter
    @Column(name = "user_type", nullable = false)
    private String userType;

    @Setter
    @Column(name = "temporary_password", nullable = false)
    private boolean temporaryPassword;

    @Setter
    @Column(name = "blocked")
    private String blocked;
}