CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE state
(
  id    BIGSERIAL PRIMARY KEY,
  state CHARACTER VARYING    NOT NULL,
  uf    CHARACTER VARYING(2) NOT NULL
);

CREATE TABLE city
(
  id       BIGSERIAL PRIMARY KEY,
  city     CHARACTER VARYING NOT NULL,
  state_id INT               NOT NULL,
  FOREIGN KEY (state_id) REFERENCES state (id)
);

CREATE TABLE user_app
(
  id                BIGSERIAL PRIMARY KEY,
  name              CHARACTER VARYING           NOT NULL,
  email             CHARACTER VARYING           NOT NULL,
  password          CHARACTER VARYING           NOT NULL,
  profile           CHARACTER VARYING           NOT NULL,
  image_url         CHARACTER VARYING           NOT NULL,
  phone_number      CHARACTER VARYING           NOT NULL,
  date_birth        DATE                        NOT NULL,
  registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  state_id          INTEGER NOT NULL,
  city_id           INTEGER NOT NULL,
  gender            CHARACTER VARYING  NOT NULL,
  FOREIGN KEY (state_id) REFERENCES state (id),
  FOREIGN KEY (city_id) REFERENCES city (id)

);

CREATE TABLE user_device
(
  id                BIGSERIAL PRIMARY KEY,
  user_app_id       INTEGER    NOT NULL,
  device_id         CHARACTER VARYING           NOT NULL
);

CREATE TABLE locality
(
  id       BIGSERIAL PRIMARY KEY,
  locality CHARACTER VARYING,
  city_id  INT NOT NULL,
  FOREIGN KEY (city_id) REFERENCES city (id)
);

CREATE TABLE category
(
  id       BIGSERIAL PRIMARY KEY,
  category CHARACTER VARYING NOT NULL
);

CREATE TABLE event
(
  id          BIGSERIAL PRIMARY KEY,
  photo_url   CHARACTER VARYING NOT NULL,
  title       CHARACTER VARYING NOT NULL,
  description CHARACTER VARYING NOT NULL,
  locality_id INT               NOT NULL,
  user_app_id INT               NOT NULL,
  status      INT               NOT NULL,
  category_id INT               NOT NULL,
  FOREIGN KEY (locality_id) REFERENCES locality (id),
  FOREIGN KEY (user_app_id) REFERENCES user_app (id),
  FOREIGN KEY (category_id) REFERENCES category (id)
);


CREATE TABLE event_date
(
  id         BIGSERIAL PRIMARY KEY,
  event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  event_hour CHARACTER VARYING NOT NULL,
  event_id   INT                         NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE price_date
(
  id         BIGSERIAL PRIMARY KEY,
  price      double  precision NOT NULL,
  event_date_id INT            NOT NULL,
  FOREIGN KEY (event_date_id) REFERENCES event_date (id)
);

CREATE TABLE favorite
(
  id            BIGSERIAL PRIMARY KEY,
  event_id      INT                         NOT NULL,
  user_app_id   INT                         NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event (id),
  FOREIGN KEY (user_app_id) REFERENCES user_app (id)
);