begin;

alter table bakery alter column id set default nextval('Bakery_SEQ');
alter table feedback alter column id set default nextval('Feedback_SEQ');
alter table person alter column id set default nextval('Person_SEQ');
alter table product alter column id set default nextval('Product_SEQ');
alter table recipe alter column id set default nextval('Recipe_SEQ');

insert into bakery(address, openTime, closeTime) values ('Czarnowiejska 66, 30-059 Kraków', '7:00', '19:00');

insert into person(name, email, passwordHash) values ('Watson Baker', 'watson@example.com', 'qwe');
insert into person(name, email, passwordHash) values ('Jane Doe', 'jane@example.com', 'asd');
insert into person(name, email, passwordHash) values ('SulfuricAcid54', 'sulfur@example.com', 'zxc');

insert into customer(person_id, deliveryAddress) values ((select id from person where name='Jane Doe'), null);
insert into customer(person_id, deliveryAddress) values ((select id from person where name='SulfuricAcid54'), 'That st., 12');

insert into employee(person_id, role, location_id) values ((select id from person where name='Watson Baker'), 'BAKER', (select id from bakery where address='Czarnowiejska 66, 30-059 Kraków'));
insert into employee(person_id, role, location_id) values ((select id from person where name='Jane Doe'), 'MANAGER', null);

insert into feedback(product_id, author_id, rating, comment) values ((select id from product where name='Macaron'), (select id from person where name='Watson Baker'), 4, null);
insert into feedback(product_id, author_id, rating, comment) values ((select id from product where name='Doughnut'), (select id from person where name='SulfuricAcid54'), 5, 'Edible :thumbs-up:');

insert into product(name, description, price) values ('Cupcake', 'Nutritious and imported', 6.33);
insert into product(name, description, price) values ('Dickery', 'Do not attempt this at home!', 420.31415);
insert into product(name, description, price) values ('Macaron', 'Flavourful filling per customer`s choice', 20);
insert into product(name, description, price) values ('Doughnut', 'Tasty douhgnut', 4.99);

insert into recipe(product_id, instructions) values ((select id from product where name='Doughnut'), 'Stir until ready');
insert into recipe(product_id, instructions) values ((select id from product where name='Cupcake'), 'Bake in over');

commit;
