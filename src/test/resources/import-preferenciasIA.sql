TRUNCATE TABLE role_privilege RESTART IDENTITY CASCADE;
TRUNCATE TABLE user_role RESTART IDENTITY CASCADE;

TRUNCATE TABLE valoraciones RESTART IDENTITY CASCADE;

TRUNCATE TABLE preferencias_ia RESTART IDENTITY CASCADE;

TRUNCATE TABLE detalles_transaccion RESTART IDENTITY CASCADE;

TRUNCATE TABLE transacciones RESTART IDENTITY CASCADE;

TRUNCATE TABLE productos RESTART IDENTITY CASCADE;

TRUNCATE TABLE categorias RESTART IDENTITY CASCADE;

TRUNCATE TABLE usuarios RESTART IDENTITY CASCADE;

TRUNCATE TABLE restaurantes RESTART IDENTITY CASCADE;

TRUNCATE TABLE privilege RESTART IDENTITY CASCADE;
TRUNCATE TABLE role RESTART IDENTITY CASCADE;

INSERT INTO usuarios (user_id, nombre, email, password, direccion, telefono)
VALUES
    (1, 'Usuario 1', 'usuario1@example.com', 'password1', 'Dirección 1', '1111111111'),
    (2, 'Usuario 2', 'usuario2@example.com', 'password2', 'Dirección 2', '2222222222');


INSERT INTO preferencias_ia (preferencia_id, historial_busqueda, gustos_identificados, alergias_identificadas, user_id)
VALUES
    (1, 'Pizza, Pasta', 'Comida Italiana', 'Gluten, Lactosa', 1),
    (2, 'Hamburguesas, Sushi', 'Comida Rápida, Japonesa', NULL, 2),
    (3, 'Ensaladas, Jugos', 'Comida Saludable', 'Nueces', 1),
    (4, 'Postres, Café', 'Dulces, Bebidas', 'Cafeína', 2);

SELECT setval('preferencias_ia_preferencia_id_seq', (SELECT MAX(preferencia_id) FROM preferencias_ia));
