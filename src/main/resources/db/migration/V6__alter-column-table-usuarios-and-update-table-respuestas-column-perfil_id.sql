
ALTER TABLE usuarios
ADD COLUMN perfil_id BIGINT,
ADD CONSTRAINT fk_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfiles(id);

ALTER TABLE respuestas RENAME COLUMN usuario_id TO perfil_id;

ALTER TABLE perfiles ADD COLUMN fecha_actualizacion VARCHAR(255) DEFAULT NULL;



