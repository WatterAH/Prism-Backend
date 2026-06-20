package com.prism.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Entidad que representa una opción de respuesta de un ejercicio.
 * Se mapea a la tabla "exercise_options". Cada ejercicio puede tener
 * entre 2 y 10 opciones, almacenadas en filas separadas.
 */
@Entity
@Table(name = "exercise_options")
public class ExerciseOption {

    // Llave primaria autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    /*
     * Relación inversa hacia Exercise (muchos a uno).
     * @JsonIgnore evita la referencia circular durante la serialización JSON:
     * sin esta anotación, al serializar un Exercise incluiría sus options,
     * y cada option volvería a incluir al Exercise, causando un bucle infinito.
     * FetchType.LAZY: el ejercicio padre solo se carga si se accede explícitamente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    @JsonIgnore
    private Exercise exercise;

    // Posición de la opción dentro del ejercicio (1, 2, 3...). Se reasigna en el controller.
    @Column(name = "option_order", nullable = false)
    private int optionOrder;

    // Texto de la opción de respuesta
    @Column(name = "text", nullable = false, length = 500)
    private String text;

    // Indica si esta opción es la respuesta correcta (o una de las correctas en MULTIPLE_CHOICE)
    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    public ExerciseOption() {
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public int getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(int optionOrder) {
        this.optionOrder = optionOrder;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
