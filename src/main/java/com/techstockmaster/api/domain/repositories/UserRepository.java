package com.techstockmaster.api.domain.repositories;

import com.techstockmaster.api.domain.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    /*
     * Declara um método que verifica se existe um usuário com o email (gmail)
     * fornecido
     */
    boolean existsByGmail(String gmail);

    boolean existsByUsername(String username);

    // Adicionar usuario
    //@Query("SELECT u FROM usernames JOIN FETCH u.roles WHERE u.username = (:username)")
    UserModel findByUsername(String username);

    @Query("SELECT u FROM UserModel u WHERE u.fullName LIKE %:name%")
    List<UserModel> filtraProNome(@Param("name") String name);


}