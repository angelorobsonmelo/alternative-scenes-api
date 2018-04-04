CREATE TABLE user_app
(
  id                BIGSERIAL PRIMARY KEY,
  name              CHARACTER VARYING           NOT NULL,
  email             CHARACTER VARYING           NOT NULL,
  password          CHARACTER VARYING           NOT NULL,
  profile           CHARACTER VARYING           NOT NULL,
  date_birth        DATE                        NOT NULL,
  registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

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

CREATE TABLE location
(
  id       BIGSERIAL PRIMARY KEY,
  location CHARACTER VARYING,
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
  location_id INT               NOT NULL,
  user_app_id INT               NOT NULL,
  status      INT               NOT NULL,
  FOREIGN KEY (location_id) REFERENCES LOCATION (id),
  FOREIGN KEY (user_app_id) REFERENCES user_app (id)
);

CREATE TABLE event_category
(
  id          BIGSERIAL PRIMARY KEY,
  event_id    INT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event (id),
  FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE price
(
  id    BIGSERIAL PRIMARY KEY,
  price DOUBLE PRECISION NOT NULL
);

CREATE TABLE event_date
(
  id         BIGSERIAL PRIMARY KEY,
  event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  event_id   INT                         NOT NULL,
  price_id   INT                         NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event (id),
  FOREIGN KEY (price_id) REFERENCES price (id)
);
