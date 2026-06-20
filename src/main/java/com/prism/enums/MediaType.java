package com.prism.enums;

/**
 * Tipo de archivo multimedia que puede asociarse a un ejercicio.
 * Se persiste como texto en la base de datos usando @Enumerated(EnumType.STRING).
 *
 * IMAGE: muestra una imagen (JPG/PNG) junto al enunciado.
 * VIDEO: muestra un reproductor de video (MP4).
 * AUDIO: muestra un reproductor de audio (MP3).
 */
public enum MediaType {
    IMAGE,
    VIDEO,
    AUDIO
}
