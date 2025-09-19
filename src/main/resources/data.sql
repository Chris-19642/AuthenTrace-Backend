INSERT INTO roles (id_rol, nombre_rol) VALUES (1, 'USUARIO') ON CONFLICT DO NOTHING;
INSERT INTO roles (id_rol, nombre_rol) VALUES (2, 'ADMIN') ON CONFLICT DO NOTHING;
