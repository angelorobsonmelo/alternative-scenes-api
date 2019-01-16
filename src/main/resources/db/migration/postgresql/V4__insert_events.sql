INSERT INTO locality (id, locality, city_id) VALUES (1, 'Rex Bar', 47);

INSERT INTO event (id, photo_url, title, description, locality_id, user_app_id,
                   status)
VALUES (1, 'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        'Lorem Ipsum',
        'é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelo',
        1, 1, 0);

        INSERT INTO event (id, photo_url, title, description, locality_id, user_app_id,
                   status)
VALUES (2, 'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        'Noite do terromoto metal',
        'Evento que vai acontecer neste dia para fazer a terra tremer!',
        1, 1, 1);

INSERT INTO category (id, category) VALUES (1, 'Show');
INSERT INTO category (id, category) VALUES (2, 'Festival');

INSERT INTO event_category (event_id, category_id) VALUES (1, 1);
INSERT INTO event_category (event_id, category_id) VALUES (2, 1);

INSERT INTO price (id, price) VALUES (1, 10.00);

INSERT INTO event_date (id, event_date, event_hour, event_id) VALUES (1, '2018-05-06', '19:00HS', 1);
INSERT INTO event_date (id, event_date, event_hour, event_id) VALUES (2, '2018-03-01', '20:00HS', 2);

INSERT INTO event_price (id, event_id, price_id, event_date_id) VALUES (1, 1, 1, 1);
INSERT INTO event_price (id, event_id, price_id, event_date_id) VALUES (2, 2, 1, 2);
