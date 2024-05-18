create function assign_id() returns trigger as $$ begin new.id := nextval('Product_SEQ'); return new; end; $$ language plpgsql;
create trigger assign_id_trigger before insert on product for each row execute function assign_id();

insert into product(name, description, price) values ('Cupcake', 'Nutritious and imported', 6.33);
insert into product(name, description, price) values ('Dickery', 'Do not attempt this at home!', 420.31415);
insert into product(name, description, price) values ('Macaron', 'Flavourful filling per customer`s choice', 20);
insert into product(name, description, price) values ('Doughnut', 'Tasty douhgnut', 4.99);
