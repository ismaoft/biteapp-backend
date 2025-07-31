TRUNCATE TABLE restaurantes RESTART IDENTITY CASCADE;
TRUNCATE TABLE categorias RESTART IDENTITY CASCADE;

INSERT INTO categorias (categoria_id, nombre)
VALUES (1, 'Comida Rápida'), (2, 'Ensaladas');

INSERT INTO restaurantes (restaurant_id, nombre, direccion, telefono)
VALUES (1, 'Restaurante ABC', '123 Calle Principal', '1234567890');

INSERT INTO productos (nombre, descripcion, precio, disponibilidad, categoria_id, restaurant_id)
VALUES
    ('Hamburguesa BBQ', 'Hamburguesa con salsa BBQ y queso cheddar', 8.99, true, 1, 1),
    ('Ensalada César', 'Ensalada con pollo, crutones y aderezo César', 5.50, true, 2, 1);

SELECT setval('productos_producto_id_seq', (SELECT MAX(producto_id) FROM productos));
