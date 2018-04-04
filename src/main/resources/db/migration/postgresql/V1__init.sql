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

CREATE TABLE event
(
  id          BIGSERIAL PRIMARY KEY,
  photo_url   CHARACTER VARYING NOT NULL,
  title       CHARACTER VARYING NOT NULL,
  description CHARACTER VARYING NOT NULL,
  location_id INT               NOT NULL,
  FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE price
(
  id    BIGSERIAL PRIMARY KEY,
  price DOUBLE PRECISION NOT NULL
);

CREATE TABLE date_event
(
  id         BIGSERIAL PRIMARY KEY,
  date_event TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  event_id   INT                         NOT NULL,
  price_id   INT                         NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event (id),
  FOREIGN KEY (price_id) REFERENCES price (id)
);



