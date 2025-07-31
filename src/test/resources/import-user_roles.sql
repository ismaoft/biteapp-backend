
TRUNCATE TABLE role_privilege RESTART IDENTITY CASCADE;
TRUNCATE TABLE user_role RESTART IDENTITY CASCADE;
TRUNCATE TABLE role RESTART IDENTITY CASCADE;
TRUNCATE TABLE privilege RESTART IDENTITY CASCADE;

INSERT INTO user_role (user_id, role_id) VALUES
                                             (1, 1),
                                             (2, 2);
