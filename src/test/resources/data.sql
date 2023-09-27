insert into users(name, email) values('Petya', 'qwerty@mail.ru');
insert into users(name, email) values('Vasya', '12345@mail.ru');
insert into users(name, email) values('Kolya', 'putyourhandsup@mail.ru');

insert into items(name, available, user_id) values('Hat', true, 1);
insert into items(name, available, user_id) values('Table', false, 1);
insert into items(name, available, user_id) values('Skirt', true, 2);
insert into items(name, available, user_id) values('Bicycle', false, 2);

insert into bookings(start, finish, item_id, user_id, status) values('2023-01-05', '2023-01-10', 1, 2, 'APPROVED');
insert into bookings(start, finish, item_id, user_id, status) values('2023-01-25', '2023-01-30', 1, 2, 'APPROVED');
insert into bookings(start, finish, item_id, user_id, status) values('2023-01-15', '2023-01-20', 1, 2, 'CANCELED');
