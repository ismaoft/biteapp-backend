INSERT INTO restaurantes (restaurant_id, nombre, direccion, telefono, categoria, horario_atencion, descripcion)
VALUES
    (1, 'Restaurante A', 'Dirección A', '1111111111', 'Italiana', '8:00 AM - 10:00 PM', 'Restaurante de comida italiana'),
    (2, 'Restaurante B', 'Dirección B', '2222222222', 'Mexicana', '9:00 AM - 9:00 PM', 'Restaurante de comida mexicana'),
    (3, 'Restaurante C', 'Dirección C', '3333333333', 'China', '10:00 AM - 8:00 PM', 'Restaurante de comida china');

SELECT setval('restaurantes_restaurant_id_seq', (SELECT MAX(restaurant_id) FROM restaurantes));
