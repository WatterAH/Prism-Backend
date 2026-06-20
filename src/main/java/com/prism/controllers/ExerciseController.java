package com.prism.controllers;

import com.prism.enums.QuestionType;
import com.prism.models.Exercise;
import com.prism.models.ExerciseOption;
import com.prism.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para el CRUD completo de ejercicios.
 * Expone los endpoints bajo /api/exercises.
 * Todas las respuestas siguen la estructura: { success, data, message }.
 */
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    // GET /api/exercises — devuelve todos los ejercicios de la base de datos
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", exercises,
                "message", "Ejercicios obtenidos correctamente"
        ));
    }

    // GET /api/exercises/{id} — busca un ejercicio por su ID primario
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .<ResponseEntity<Map<String, Object>>>map(ex -> ResponseEntity.ok(Map.of(
                        "success", true,
                        "data", ex,
                        "message", "Ejercicio encontrado"
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false,
                        "data", Map.of(),
                        "message", "Ejercicio no encontrado"
                )));
    }

    // POST /api/exercises — crea un nuevo ejercicio con sus opciones
    @PostMapping
    public ResponseEntity<Map<String, Object>> createExercise(@RequestBody Exercise exercise) {
        // Valida reglas de negocio antes de persistir
        String validation = validate(exercise);
        if (validation != null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "data", Map.of(),
                    "message", validation
            ));
        }
        // Asigna optionOrder secuencial (1, 2, 3...) antes de guardar
        normalizeOptionOrder(exercise);
        Exercise saved = exerciseRepository.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "data", saved,
                "message", "Ejercicio creado correctamente"
        ));
    }

    // PUT /api/exercises/{id} — reemplaza un ejercicio existente por completo
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        if (!exerciseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "data", Map.of(),
                    "message", "Ejercicio no encontrado"
            ));
        }
        String validation = validate(exercise);
        if (validation != null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "data", Map.of(),
                    "message", validation
            ));
        }
        // Fuerza el ID del path para que JPA haga UPDATE y no INSERT
        exercise.setExerciseId(id);
        normalizeOptionOrder(exercise);
        Exercise saved = exerciseRepository.save(exercise);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", saved,
                "message", "Ejercicio actualizado correctamente"
        ));
    }

    // DELETE /api/exercises/{id} — elimina el ejercicio y sus opciones (CASCADE en BD)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteExercise(@PathVariable Long id) {
        if (!exerciseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "data", Map.of(),
                    "message", "Ejercicio no encontrado"
            ));
        }
        exerciseRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", Map.of(),
                "message", "Ejercicio eliminado correctamente"
        ));
    }

    /**
     * Valida las reglas de negocio de un ejercicio antes de persistirlo.
     * Retorna el mensaje de error si falla alguna regla, o null si todo es válido.
     *
     * Reglas:
     *  1. Título no vacío
     *  2. Instrucciones no vacías
     *  3. Al menos 2 opciones
     *  4. Al menos 1 opción marcada como correcta
     *  5. SINGLE_CHOICE no puede tener más de 1 opción correcta
     *  6. Ninguna opción puede tener texto vacío
     */
    private String validate(Exercise ex) {
        if (ex.getTitle() == null || ex.getTitle().isBlank()) {
            return "El título es obligatorio";
        }
        if (ex.getInstructions() == null || ex.getInstructions().isBlank()) {
            return "Las instrucciones son obligatorias";
        }
        List<ExerciseOption> opts = ex.getOptions();
        if (opts == null || opts.size() < 2) {
            return "El ejercicio debe tener al menos 2 opciones";
        }
        long correctCount = opts.stream().filter(ExerciseOption::isCorrect).count();
        if (correctCount == 0) {
            return "Debe haber al menos una opción correcta";
        }
        if (ex.getQuestionType() == QuestionType.SINGLE_CHOICE && correctCount > 1) {
            return "Una pregunta de opción única solo puede tener una respuesta correcta";
        }
        for (ExerciseOption o : opts) {
            if (o.getText() == null || o.getText().isBlank()) {
                return "Todas las opciones deben tener texto";
            }
        }
        return null;
    }

    /**
     * Reasigna el campo optionOrder de cada opción de forma secuencial (1, 2, 3...).
     * Esto evita huecos o duplicados en el orden cuando el usuario reordena o elimina opciones.
     */
    private void normalizeOptionOrder(Exercise ex) {
        List<ExerciseOption> opts = ex.getOptions();
        if (opts == null) return;
        for (int i = 0; i < opts.size(); i++) {
            opts.get(i).setOptionOrder(i + 1);
        }
    }
}
