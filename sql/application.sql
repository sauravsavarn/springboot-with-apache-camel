CREATE table items(
    item_id SERIAL,
    sku text not null ,
    item_description text default null,
    price numeric(5,2),
    purchaseDateTime timestamp null default current_timestamp
);

ALTER TABLE items ALTER COLUMN price TYPE decimal(10,2);

select * from items;