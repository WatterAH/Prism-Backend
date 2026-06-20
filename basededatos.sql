DROP DATABASE IF EXISTS prism_db;
CREATE DATABASE prism_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE prism_db;

CREATE TABLE users (
    user_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    role      VARCHAR(20)
) ENGINE=InnoDB;

INSERT INTO users (username, password, role) VALUES ('admin', '1234', 'ADMIN');

CREATE TABLE exercises (
    exercise_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    instructions     TEXT         NOT NULL,
    explanation      TEXT,

    question_type    VARCHAR(20)  NOT NULL DEFAULT 'SINGLE_CHOICE',
    difficulty       VARCHAR(10)  NOT NULL DEFAULT 'MEDIUM',

    media_type       VARCHAR(20),
    media_path       VARCHAR(500),

    chart_type       VARCHAR(20),
    chart_title      VARCHAR(255),
    x_axis_label     VARCHAR(100),
    y_axis_label     VARCHAR(100),
    chart_data_json  TEXT,

    primary_color    VARCHAR(7) NOT NULL DEFAULT '#16140f',
    secondary_color  VARCHAR(7) NOT NULL DEFAULT '#737373',

    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE exercise_options (
    option_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    exercise_id   BIGINT      NOT NULL,
    option_order  INT         NOT NULL,
    text          VARCHAR(500) NOT NULL,
    is_correct    BOOLEAN     NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_option_exercise
        FOREIGN KEY (exercise_id)
        REFERENCES exercises (exercise_id)
        ON DELETE CASCADE,

    INDEX idx_exercise (exercise_id)
) ENGINE=InnoDB;

INSERT INTO exercises (title, instructions, question_type, difficulty, chart_type, chart_title, x_axis_label, y_axis_label, chart_data_json, primary_color, secondary_color)
VALUES (
    'Top Global de Streaming',
    'Observa la gráfica de barras que muestra los millones de reproducciones acumuladas en el último mes. Según los datos, ¿cuál de estas canciones es la más escuchada?',
    'SINGLE_CHOICE',
    'EASY',
    'BAR',
    'Reproducciones (millones)',
    'Canción',
    'Reproducciones',
    '[{"label":"Reminder","value":85},{"label":"Starboy","value":70},{"label":"Blinding Lights","value":95},{"label":"Save Your Tears","value":80}]',
    '#16140f',
    '#737373'
);
SET @ex1 = LAST_INSERT_ID();
INSERT INTO exercise_options (exercise_id, option_order, text, is_correct) VALUES
    (@ex1, 1, 'Reminder',         FALSE),
    (@ex1, 2, 'Starboy',          FALSE),
    (@ex1, 3, 'Blinding Lights',  TRUE),
    (@ex1, 4, 'Save Your Tears',  FALSE);

INSERT INTO exercises (title, instructions, question_type, difficulty, chart_type, chart_title, chart_data_json, primary_color, secondary_color)
VALUES (
    'Venta de Boletos en Festivales',
    'La siguiente gráfica circular representa el porcentaje de entradas vendidas para los principales artistas de un festival urbano. ¿Qué artista logró vender exactamente el 40% de los boletos?',
    'SINGLE_CHOICE',
    'MEDIUM',
    'PIE',
    'Distribución de boletos vendidos (%)',
    '[{"label":"Travis Scott","value":40},{"label":"Drake","value":30},{"label":"Kanye West","value":20},{"label":"Kendrick Lamar","value":10}]',
    '#7c3aed',
    '#f59e0b'
);
SET @ex2 = LAST_INSERT_ID();
INSERT INTO exercise_options (exercise_id, option_order, text, is_correct) VALUES
    (@ex2, 1, 'Drake',            FALSE),
    (@ex2, 2, 'Kanye West',       FALSE),
    (@ex2, 3, 'Kendrick Lamar',   FALSE),
    (@ex2, 4, 'Travis Scott',     TRUE),
    (@ex2, 5, 'J. Cole',          FALSE);

INSERT INTO exercises (title, instructions, question_type, difficulty, chart_type, chart_title, x_axis_label, y_axis_label, chart_data_json, primary_color, secondary_color)
VALUES (
    'Escalando en el Billboard Hot 100',
    'Esta gráfica de líneas sigue el progreso de un sencillo durante sus primeras 4 semanas de lanzamiento. Teniendo en cuenta que en el Billboard el #1 es lo más alto, ¿en qué semana la canción tuvo su mejor posición?',
    'SINGLE_CHOICE',
    'HARD',
    'LINE',
    'Posición semanal',
    'Semana',
    'Posición (menor = mejor)',
    '[{"label":"Semana 1","value":45},{"label":"Semana 2","value":12},{"label":"Semana 3","value":3},{"label":"Semana 4","value":8}]',
    '#dc2626',
    '#fca5a5'
);
SET @ex3 = LAST_INSERT_ID();
INSERT INTO exercise_options (exercise_id, option_order, text, is_correct) VALUES
    (@ex3, 1, 'Semana 1',          FALSE),
    (@ex3, 2, 'Semana 2',          FALSE),
    (@ex3, 3, 'Semana 3',          TRUE),
    (@ex3, 4, 'Semana 4',          FALSE),
    (@ex3, 5, 'Se mantuvo igual',  FALSE);
