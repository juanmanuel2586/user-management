
-- Usuario
CREATE TABLE IF NOT EXISTS "user" (
  id            CHAR(36) PRIMARY KEY,                    -- UUID
  name          VARCHAR(100) NOT NULL,
  email         VARCHAR(320) NOT NULL,                   -- único
  password_hash VARCHAR(255) NOT NULL,                   -- guarda hash, no la clave en claro
  token         VARCHAR(1024) NOT NULL,                  -- JWT o UUID (enunciado pide persistirlo)
  is_active     BOOLEAN NOT NULL DEFAULT TRUE,
  created       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_login    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_user_email ON "user"(email);
CREATE INDEX IF NOT EXISTS ix_user_last_login ON "user"(last_login);



-- Teléfono
CREATE TABLE IF NOT EXISTS "phone" (
  id          SERIAL PRIMARY KEY,                    -- Autogenerado
  user_id     CHAR(36) NOT NULL,
  number      VARCHAR(30) NOT NULL,
  citycode    VARCHAR(10) NOT NULL,
  contrycode  VARCHAR(10) NOT NULL,
  CONSTRAINT fk_phone_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS ix_phone_user_id ON "phone"(user_id);
