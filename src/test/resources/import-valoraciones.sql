TRUNCATE TABLE detalles_transaccion RESTART IDENTITY CASCADE;
TRUNCATE TABLE valoraciones RESTART IDENTITY CASCADE;
TRUNCATE TABLE transacciones RESTART IDENTITY CASCADE;

TRUNCATE TABLE productos RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuarios RESTART IDENTITY CASCADE;


INSERT INTO usuarios (user_id, nombre, email, password, direccion, telefono, fecha_creacion, ultimo_login)
VALUES
    (1, 'Juan Pérez', 'juan.perez@example.com', 'password123', '123 Main St', '123-456-7890', '2024-09-01 08:00:00', '2024-10-01 09:00:00'),
    (2, 'Ana López', 'ana.lopez@example.com', 'password123', '456 Elm St', '987-654-3210', '2024-09-02 10:00:00', '2024-10-01 10:00:00');

INSERT INTO productos (producto_id, nombre, descripcion, precio, ingredientes, disponibilidad, categoria_id, restaurant_id)
VALUES
    (1, 'Pizza Margherita', 'Pizza con tomate, albahaca y queso', 12.50, 'Tomate, Albahaca, Queso', TRUE, 1, 1),
    (2, 'Hamburguesa', 'Hamburguesa clásica con queso y tomate', 8.00, 'Carne, Queso, Tomate', TRUE, 2, 1);

INSERT INTO valoraciones (valoracion_id, calificacion, comentario, fecha_resena, user_id, producto_id)
VALUES
    (1, 4, 'Muy buen sabor, pero algo costosa', '2024-09-01 12:00:00', 1, 1),
    (2, 5, 'Excelente hamburguesa, muy jugosa', '2024-09-02 14:00:00', 2, 2);

INSERT INTO transacciones (transaccion_id, user_id, restaurant_id, fecha_transaccion, estado, total)
VALUES
    (1, 1, 1, '2024-09-01 10:00:00', 'Pendiente', 50.75),
    (2, 1, 2, '2024-09-02 12:30:00', 'Completado', 30.50),
    (3, 2, 1, '2024-09-03 15:45:00', 'Cancelado', 20.00),
    (4, 2, 3, '2024-09-04 08:20:00', 'Pendiente', 100.00);

SELECT setval('usuarios_user_id_seq', (SELECT MAX(user_id) FROM usuarios));
SELECT setval('productos_producto_id_seq', (SELECT MAX(producto_id) FROM productos));
SELECT setval('valoraciones_valoracion_id_seq', (SELECT MAX(valoracion_id) FROM valoraciones));
SELECT setval('transacciones_transaccion_id_seq', (SELECT MAX(transaccion_id) FROM transacciones));
