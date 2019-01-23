INSERT INTO locality (id, name, city_id) VALUES (1, 'Rex Bar', 47);

INSERT INTO category (id, name) VALUES (1, 'Show');
INSERT INTO category (id, name) VALUES (2, 'Festival');

INSERT INTO event (id, photo_url, title, description, locality_id, user_app_id,
                   status, category_id)
VALUES (1, 'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        'Lorem Ipsum',
        'é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelo',
        1, 1, false, 2);

        INSERT INTO event (id, photo_url, title, description, locality_id, user_app_id,
                   status, category_id)
VALUES (2, 'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        'Noite do terromoto metal',
        'Evento que vai acontecer neste dia para fazer a terra tremer!',
        1, 1, true, 1);

INSERT INTO event_date (id, date, event_hour, event_id) VALUES (1, '2018-05-06', '19:00HS', 1);
INSERT INTO event_date (id, date, event_hour, event_id) VALUES (2, '2018-03-01', '20:00HS', 2);

INSERT INTO price_date (id, price, event_date_id) VALUES (1, 10.00, 1);
INSERT INTO price_date (id, price, event_date_id) VALUES (2, 25.00, 2);
