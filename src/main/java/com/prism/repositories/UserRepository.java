package com.prism.repositories;

import com.prism.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad User.
 *
 * Al extender JpaRepository, Spring Data JPA genera automáticamente la implementación
 * en tiempo de ejecución: findAll(), findById(), save(), deleteById(), etc.
 * No es necesario escribir SQL ni implementar ningún método manualmente.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Spring analiza el nombre del método y construye automáticamente
     * la consulta equivalente a: SELECT * FROM users WHERE username = ? AND password = ?
     * Devuelve Optional para forzar al llamador a manejar el caso en que no exista el usuario.
     */
    Optional<User> findByUsernameAndPassword(String username, String password);
}
