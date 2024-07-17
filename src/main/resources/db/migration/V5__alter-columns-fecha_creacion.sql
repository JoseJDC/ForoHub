
ALTER TABLE usuarios ALTER COLUMN fecha_creacion DROP NOT NULL,
ALTER COLUMN fecha_creacion TYPE VARCHAR(50) USING fecha_creacion::VARCHAR(50);

ALTER TABLE perfiles ALTER COLUMN fecha_creacion DROP NOT NULL,
ALTER COLUMN fecha_creacion TYPE VARCHAR(50) USING fecha_creacion::VARCHAR(50);

ALTER TABLE topicos ALTER COLUMN fecha_creacion DROP NOT NULL,
ALTER COLUMN fecha_creacion TYPE VARCHAR(50) USING fecha_creacion::VARCHAR(50);

ALTER TABLE respuestas ALTER COLUMN fecha_creacion DROP NOT NULL,
ALTER COLUMN fecha_creacion TYPE VARCHAR(50) USING fecha_creacion::VARCHAR(50);


