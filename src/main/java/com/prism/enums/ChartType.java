package com.prism.enums;

/**
 * Tipo de gráfica que puede asociarse a un ejercicio.
 * Se persiste como texto en la base de datos usando @Enumerated(EnumType.STRING).
 *
 * BAR   : gráfica de barras verticales.
 * LINE  : gráfica de líneas con puntos.
 * PIE   : gráfica de pastel (sectores circulares).
 * DONUT : igual que PIE pero con hueco en el centro.
 * AREA  : línea con área rellena debajo de la curva.
 *
 * El renderizado de cada tipo lo maneja el componente D3Chart en el frontend.
 */
public enum ChartType {
    BAR,
    LINE,
    PIE,
    DONUT,
    AREA
}
