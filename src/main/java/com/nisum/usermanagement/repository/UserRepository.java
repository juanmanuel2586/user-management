package com.nisum.usermanagement.repository;

import com.nisum.usermanagement.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // Buscar por email (ignorando mayúsculas/minúsculas)
    Optional<User> findByEmailIgnoreCase(String email);

    // Verificar si existe email (para validaciones de alta)
    boolean existsByEmailIgnoreCase(String email);

    // Cargar un usuario con sus teléfonos en una sola consulta (para detalle)
    @Query("select u from User u left join fetch u.phones where u.id = :id")
    Optional<User> findByIdWithPhones(@Param("id") String id);

    // Cargar por email con teléfonos (útil en login / detalle)
    @Query("select u from User u left join fetch u.phones where lower(u.email) = lower(:email)")
    Optional<User> findByEmailWithPhones(@Param("email") String email);
}