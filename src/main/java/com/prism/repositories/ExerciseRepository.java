package com.prism.repositories;

import com.prism.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad Exercise.
 *
 * Al extender JpaRepository, Spring Data JPA genera automáticamente:
 * findAll(), findById(), save(), existsById(), deleteById(), entre otros.
 * Gracias al cascade definido en Exercise, al eliminar un ejercicio
 * también se eliminan sus opciones de forma automática.
 */
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
