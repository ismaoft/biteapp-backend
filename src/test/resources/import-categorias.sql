TRUNCATE TABLE categorias RESTART IDENTITY CASCADE;

INSERT INTO categorias (categoria_id, nombre)
VALUES
    (1, 'Bebidas'),
    (2, 'Entradas'),
    (3, 'Platos fuertes'),
    (4, 'Postres');

SELECT setval('categorias_categoria_id_seq', (SELECT MAX(categoria_id) FROM categorias));
