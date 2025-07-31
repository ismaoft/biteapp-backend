TRUNCATE TABLE detalles_transaccion RESTART IDENTITY CASCADE;
TRUNCATE TABLE productos RESTART IDENTITY CASCADE;
TRUNCATE TABLE transacciones RESTART IDENTITY CASCADE;

INSERT INTO productos (producto_id, nombre, descripcion, precio, ingredientes, disponibilidad, categoria_id, restaurant_id)
VALUES
    (1, 'Pizza Margherita', 'Pizza con tomate, albahaca y queso', 12.50, 'Tomate, Albahaca, Queso', TRUE, 1, 1),
    (2, 'Hamburguesa', 'Hamburguesa clásica con queso y tomate', 8.00, 'Carne, Queso, Tomate', TRUE, 2, 1);

INSERT INTO transacciones (transaccion_id, user_id, restaurant_id, fecha_transaccion, estado, total)
VALUES
    (1, 1, 1, '2024-09-01 10:00:00', 'Pendiente', 25.00),
    (2, 1, 1, '2024-09-02 12:30:00', 'Completado', 30.00);

INSERT INTO detalles_transaccion (detalle_id, cantidad, precio_unitario, subtotal, transaccion_id, producto_id)
VALUES
    (1, 2, 12.50, 25.00, 1, 1),
    (2, 3, 8.00, 24.00, 2, 2);

SELECT setval('detalles_transaccion_detalle_id_seq', (SELECT MAX(detalle_id) FROM detalles_transaccion));
SELECT setval('productos_producto_id_seq', (SELECT MAX(producto_id) FROM productos));
SELECT setval('transacciones_transaccion_id_seq', (SELECT MAX(transaccion_id) FROM transacciones));
