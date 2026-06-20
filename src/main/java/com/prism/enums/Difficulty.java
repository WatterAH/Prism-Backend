package com.prism.enums;

/**
 * Nivel de dificultad del ejercicio.
 * Se persiste como texto en la base de datos usando @Enumerated(EnumType.STRING).
 *
 * El frontend lo mapea a colores: EASY = verde, MEDIUM = amarillo, HARD = rojo.
 */
public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}
