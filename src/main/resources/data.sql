-- ==== DATA: USUARIOS Y TELÉFONOS ====


INSERT INTO "user" (id, name, email, password_hash, token, is_active, created, modified, last_login)
VALUES
  ('a1b2c3d4-e5f6-7890-abcd-1234567890ab', 'Juan Pérez', 'juan.perez@example.com', 'hash1', 'token1', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('b2c3d4e5-f6a7-8901-bcde-2345678901bc', 'María Gómez', 'maria.gomez@example.com', 'hash2', 'token2', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('c3d4e5f6-a7b8-9012-cdef-3456789012cd', 'Carlos Ruiz', 'carlos.ruiz@example.com', 'hash3', 'token3', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO "phone" (user_id, number, citycode, contrycode)
VALUES
  ('a1b2c3d4-e5f6-7890-abcd-1234567890ab', '123456789', '1', '57'),
  ('b2c3d4e5-f6a7-8901-bcde-2345678901bc', '987654321', '2', '34'),
  ('c3d4e5f6-a7b8-9012-cdef-3456789012cd', '555666777', '3', '52');
