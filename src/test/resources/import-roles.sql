TRUNCATE TABLE role_privilege RESTART IDENTITY CASCADE;
TRUNCATE TABLE user_role RESTART IDENTITY CASCADE;
TRUNCATE TABLE role RESTART IDENTITY CASCADE;
TRUNCATE TABLE privilege RESTART IDENTITY CASCADE;


INSERT INTO role (id, name) VALUES
                                (1, 'ADMIN'),
                                (2, 'USER');
