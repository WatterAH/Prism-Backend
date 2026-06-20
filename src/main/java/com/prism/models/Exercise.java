package com.prism.models;

import com.prism.enums.ChartType;
import com.prism.enums.Difficulty;
import com.prism.enums.MediaType;
import com.prism.enums.QuestionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad principal del sistema. Representa un ejercicio académico.
 * Se mapea a la tabla "exercises" en la base de datos MySQL.
 *
 * Cada ejercicio tiene: enunciado, opciones de respuesta, dificultad,
 * tipo de pregunta, una gráfica opcional y un archivo multimedia opcional.
 */
@Entity
@Table(name = "exercises")
public class Exercise {

    // Llave primaria autoincremental gestionada por MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(nullable = false)
    private String title;

    // TEXT en lugar de VARCHAR para soportar enunciados largos
    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;

    // Explicación opcional que se muestra al alumno después de evaluar su respuesta
    @Column(columnDefinition = "TEXT")
    private String explanation;

    /*
     * EnumType.STRING persiste el valor como texto ("SINGLE_CHOICE", "MULTIPLE_CHOICE")
     * en lugar de un índice numérico, lo que hace los datos legibles en la BD
     * y evita errores si se reordena la declaración del enum en el futuro.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, length = 20)
    private QuestionType questionType = QuestionType.SINGLE_CHOICE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Difficulty difficulty = Difficulty.MEDIUM;

    // Tipo de archivo multimedia adjunto (IMAGE, VIDEO o AUDIO). Puede ser nulo.
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", length = 20)
    private MediaType mediaType;

    // URL del archivo multimedia. Solo se usa si mediaType no es nulo.
    @Column(name = "media_path", length = 500)
    private String mediaPath;

    // Tipo de gráfica (BAR, LINE, PIE, DONUT, AREA). Puede ser nulo si el ejercicio no tiene gráfica.
    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type", length = 20)
    private ChartType chartType;

    @Column(name = "chart_title")
    private String chartTitle;

    // Etiquetas de los ejes para gráficas de tipo BAR, LINE y AREA
    @Column(name = "x_axis_label", length = 100)
    private String xAxisLabel;

    @Column(name = "y_axis_label", length = 100)
    private String yAxisLabel;

    // JSON con los datos de la gráfica. Formato: [{"label":"...", "value": 0}, ...]
    @Column(name = "chart_data_json", columnDefinition = "TEXT")
    private String chartDataJson;

    // Colores primario y secundario para la paleta de la gráfica (formato HEX #RRGGBB)
    @Column(name = "primary_color", nullable = false, length = 7)
    private String primaryColor = "#16140f";

    @Column(name = "secondary_color", nullable = false, length = 7)
    private String secondaryColor = "#737373";

    // Fechas de auditoría gestionadas automáticamente por los callbacks @PrePersist y @PreUpdate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
     * Relación uno a muchos con ExerciseOption.
     *
     * cascade = ALL: cualquier operación sobre el ejercicio (guardar, eliminar)
     *   se propaga automáticamente a sus opciones.
     * orphanRemoval = true: si una opción se elimina de la lista en memoria,
     *   también se borra de la base de datos al hacer save().
     * fetch = EAGER: las opciones se cargan junto con el ejercicio en la misma consulta.
     * @OrderBy: garantiza que las opciones lleguen al frontend ordenadas por su posición.
     */
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("optionOrder ASC")
    private List<ExerciseOption> options = new ArrayList<>();

    // Constructor vacío obligatorio para que JPA pueda instanciar la clase
    public Exercise() {
    }

    // Se ejecuta automáticamente antes de insertar el registro por primera vez
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // Se ejecuta automáticamente antes de cada actualización del registro
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public QuestionType getQuestionType() { return questionType; }
    public void setQuestionType(QuestionType questionType) { this.questionType = questionType; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public String getMediaPath() { return mediaPath; }
    public void setMediaPath(String mediaPath) { this.mediaPath = mediaPath; }

    public ChartType getChartType() { return chartType; }
    public void setChartType(ChartType chartType) { this.chartType = chartType; }

    public String getChartTitle() { return chartTitle; }
    public void setChartTitle(String chartTitle) { this.chartTitle = chartTitle; }

    public String getxAxisLabel() { return xAxisLabel; }
    public void setxAxisLabel(String xAxisLabel) { this.xAxisLabel = xAxisLabel; }

    public String getyAxisLabel() { return yAxisLabel; }
    public void setyAxisLabel(String yAxisLabel) { this.yAxisLabel = yAxisLabel; }

    public String getChartDataJson() { return chartDataJson; }
    public void setChartDataJson(String chartDataJson) { this.chartDataJson = chartDataJson; }

    public String getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(String primaryColor) { this.primaryColor = primaryColor; }

    public String getSecondaryColor() { return secondaryColor; }
    public void setSecondaryColor(String secondaryColor) { this.secondaryColor = secondaryColor; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public List<ExerciseOption> getOptions() { return options; }
    public void setOptions(List<ExerciseOption> options) {
        this.options.clear();
        if (options != null) {
            for (ExerciseOption o : options) {
                o.setExercise(this);
                this.options.add(o);
            }
        }
    }
}
