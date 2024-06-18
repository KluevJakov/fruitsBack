ALTER TABLE order_table ADD customer_id UUID;
ALTER TABLE order_table ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (customer_id) REFERENCES users (id);
