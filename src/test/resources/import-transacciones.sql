TRUNCATE TABLE transacciones RESTART IDENTITY CASCADE;

INSERT INTO transacciones (transaccion_id, user_id, restaurant_id, fecha_transaccion, estado, total)
VALUES
    (1, 1, 1, '2024-09-01 10:00:00', 'Pendiente', 50.75),
    (2, 1, 2, '2024-09-02 12:30:00', 'Completado', 30.50),
    (3, 2, 1, '2024-09-03 15:45:00', 'Cancelado', 20.00),
    (4, 2, 3, '2024-09-04 08:20:00', 'Pendiente', 100.00);

SELECT setval('transacciones_transaccion_id_seq', (SELECT MAX(transaccion_id) FROM transacciones));
