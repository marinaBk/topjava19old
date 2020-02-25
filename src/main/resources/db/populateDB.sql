DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
/*
INSERT INTO meals (datetime, description, calories) VALUES
    (LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
    (LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
    (LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
    (LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
    (LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
    (LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
    (LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
   );
*/
