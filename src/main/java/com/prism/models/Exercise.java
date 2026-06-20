package com.prism.models;

import com.prism.enums.ChartType;
import com.prism.enums.Difficulty;
import com.prism.enums.MediaType;
import com.prism.enums.QuestionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, length = 20)
    private QuestionType questionType = QuestionType.SINGLE_CHOICE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Difficulty difficulty = Difficulty.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", length = 20)
    private MediaType mediaType;

    @Column(name = "media_path", length = 500)
    private String mediaPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type", length = 20)
    private ChartType chartType;

    @Column(name = "chart_title")
    private String chartTitle;

    @Column(name = "x_axis_label", length = 100)
    private String xAxisLabel;

    @Column(name = "y_axis_label", length = 100)
    private String yAxisLabel;

    @Column(name = "chart_data_json", columnDefinition = "TEXT")
    private String chartDataJson;

    @Column(name = "primary_color", nullable = false, length = 7)
    private String primaryColor = "#16140f";

    @Column(name = "secondary_color", nullable = false, length = 7)
    private String secondaryColor = "#737373";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("optionOrder ASC")
    private List<ExerciseOption> options = new ArrayList<>();

    public Exercise() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

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
