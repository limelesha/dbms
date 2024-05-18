begin;

alter table product alter column id set default nextval('Product_SEQ');
alter table recipe alter column id set default nextval('Recipe_SEQ');
alter table bakery alter column id set default nextval('Bakery_SEQ');

insert into product(name, description, price) values ('Cupcake', 'Nutritious and imported', 6.33);
insert into product(name, description, price) values ('Dickery', 'Do not attempt this at home!', 420.31415);
insert into product(name, description, price) values ('Macaron', 'Flavourful filling per customer`s choice', 20);
insert into product(name, description, price) values ('Doughnut', 'Tasty douhgnut', 4.99);

insert into recipe(product_id, instructions) values ((select id from product where name='Doughnut'), 'Stir until ready');
insert into recipe(product_id, instructions) values ((select id from product where name='Cupcake'), 'Bake in over');

insert into bakery(address, openTime, closeTime) values ('Czarnowiejska 66, 30-059 Kraków', '7:00', '19:00');

commit;
