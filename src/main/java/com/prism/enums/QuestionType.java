package com.prism.enums;

/**
 * Tipo de pregunta del ejercicio.
 * Se persiste como texto en la base de datos usando @Enumerated(EnumType.STRING),
 * lo que hace los datos legibles directamente en MySQL.
 *
 * SINGLE_CHOICE: solo una opción puede ser correcta (radio button en el frontend).
 * MULTIPLE_CHOICE: varias opciones pueden ser correctas (checkboxes en el frontend).
 */
public enum QuestionType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICE
}
