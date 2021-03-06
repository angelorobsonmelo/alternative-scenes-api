INSERT INTO locality (id, name, address, latitude, longitude, city_id)
VALUES (1, 'Rex Jazz Bar', 'Rua Sá e Albuquerque - Jaraguá, Maceió - AL', -9.6708037, -35.7274909,  47);

INSERT INTO category (id, name) VALUES (1, 'Show');
INSERT INTO category (id, name) VALUES (2, 'Festival');

INSERT INTO event (id, image_url, image_thumb_url, locality_id, user_app_id, category_id)
VALUES (1, 'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
         'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        1, 1, 2);

        INSERT INTO event (id, image_url, image_thumb_url, locality_id, user_app_id,
                   status, category_id)
VALUES (2, 'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        1, 1, 'APPROVED', 1);

         INSERT INTO event (id, image_url, image_thumb_url, locality_id, user_app_id,
                   status, category_id)
VALUES (3, 'https://i.pinimg.com/originals/62/78/1e/62781ef2c651d2de4f37c6f5524660ab.jpg',
        'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        1, 1, 'APPROVED', 1);

        INSERT INTO event (id, image_url, image_thumb_url, locality_id, user_app_id,
                   status, category_id)
VALUES (4, 'https://previews.customer.envatousercontent.com/files/29437985/590-blackened.png',
        'https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg',
        1, 1, 'APPROVED', 1);

INSERT INTO event_date (id, date, event_hour, event_id) VALUES (1, '2018-05-06', '19:00HS', 1);
INSERT INTO event_date (id, date, event_hour, event_id) VALUES (2, '2018-08-02', '20:00HS', 2);
INSERT INTO event_date (id, date, event_hour, event_id) VALUES (3, '2018-03-01', '20:00HS', 3);
INSERT INTO event_date (id, date, event_hour, event_id) VALUES (4, '2018-06-03', '20:00HS', 4);
INSERT INTO event_date (id, date, event_hour, event_id) VALUES (5, '2018-03-08', '20:00HS', 4);

INSERT INTO price_date (id, price, event_date_id) VALUES (1, 10.00, 1);
INSERT INTO price_date (id, price, event_date_id) VALUES (2, 25.00, 2);
INSERT INTO price_date (id, price, event_date_id) VALUES (3, 25.00, 3);
INSERT INTO price_date (id, price, event_date_id) VALUES (4, 25.00, 4);
INSERT INTO price_date (id, price, event_date_id) VALUES (5, 25.00, 5);
