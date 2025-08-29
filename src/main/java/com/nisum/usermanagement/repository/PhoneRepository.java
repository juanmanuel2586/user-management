package com.nisum.usermanagement.repository;

import com.nisum.usermanagement.domain.Phone;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    // Teléfonos de un usuario
    List<Phone> findByUser_Id(String userId);
    // o si prefieres el campo directo en la entidad: List<Phone> findByUserId(UUID userId);

    // ¿Existe un número para ese usuario? (evita duplicados)
    boolean existsByUser_IdAndNumber(String userId, String number);

    // Contar teléfonos del usuario (métrica/validación)
    long countByUser_Id(String userId);

    // Borrado en bloque por usuario (útil en replace-all de teléfonos)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Phone p where p.user.id = :userId")
    int deleteByUserId(@Param("userId") String userId);
}