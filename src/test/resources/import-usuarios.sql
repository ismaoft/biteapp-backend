TRUNCATE TABLE role_privilege RESTART IDENTITY CASCADE;
TRUNCATE TABLE user_role RESTART IDENTITY CASCADE;
TRUNCATE TABLE role RESTART IDENTITY CASCADE;
TRUNCATE TABLE privilege RESTART IDENTITY CASCADE;

INSERT INTO usuarios (user_id, nombre, email, password, direccion, telefono)
VALUES
    (1, 'Usuario 1', 'usuario1@example.com', 'password1', 'Dirección 1', '1111111111'),
    (2, 'Usuario 2', 'usuario2@example.com', 'password2', 'Dirección 2', '2222222222');

SELECT setval('usuarios_user_id_seq', (SELECT MAX(user_id) FROM usuarios));
