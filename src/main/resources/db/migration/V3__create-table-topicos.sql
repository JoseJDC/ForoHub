
CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL UNIQUE,
    mensaje VARCHAR(1500) NOT NULL UNIQUE,
    perfil_id BIGINT,
    curso VARCHAR(50) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfiles (id),
    CONSTRAINT chk_curso CHECK
    ( curso IN (
             'DESARROLLO_WEB',
             'INTELIGENCIA_ARTIFICIAL',
             'CIENCIA_DE_DATOS',
             'DESARROLLO_MOVIL',
             'CIBERSEGURIDAD' ))
);

CREATE INDEX idx_titulo ON topicos (titulo);
CREATE INDEX idx_mensaje ON topicos (mensaje);

