
CREATE TABLE respuestas (
    id BIGSERIAL PRIMARY KEY,
    mensaje VARCHAR(1500) NOT NULL,
    topico_id BIGINT,
    usuario_id BIGINT,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_topico_id FOREIGN KEY (topico_id) REFERENCES topicos (id),
    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);

CREATE INDEX idx_topico_id ON respuestas (topico_id);
CREATE INDEX idx_usuario_id ON respuestas (usuario_id);

