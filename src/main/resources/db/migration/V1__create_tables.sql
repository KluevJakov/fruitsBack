CREATE TABLE Category (
  id UUID NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE Ingredient (
  id UUID NOT NULL,
   name VARCHAR(255),
   translate VARCHAR(255),
   category_id UUID,
   CONSTRAINT pk_ingredient PRIMARY KEY (id)
);

CREATE TABLE bouquet (
  uuid UUID NOT NULL,
   id INTEGER,
   name VARCHAR(255),
   img OID,
   quantity INTEGER,
   description VARCHAR(255),
   section VARCHAR(255),
   price VARCHAR(255),
   CONSTRAINT pk_bouquet PRIMARY KEY (uuid)
);

CREATE TABLE bouquet_composition (
  bouquet_uuid UUID NOT NULL,
   composition_id UUID NOT NULL
);

ALTER TABLE bouquet_composition ADD CONSTRAINT fk_boucom_on_bouquet FOREIGN KEY (bouquet_uuid) REFERENCES bouquet (uuid);

ALTER TABLE bouquet_composition ADD CONSTRAINT fk_boucom_on_ingredient FOREIGN KEY (composition_id) REFERENCES ingredient (id);

ALTER TABLE Ingredient ADD CONSTRAINT FK_INGREDIENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES Category (id);

CREATE TABLE order_table (
  id UUID NOT NULL,
   customer_name VARCHAR(255),
   phone_customer VARCHAR(255),
   email VARCHAR(255),
   phone_receiver VARCHAR(255),
   delivery VARCHAR(255),
   delivery_date TIMESTAMP WITHOUT TIME ZONE,
   address VARCHAR(255),
   comment VARCHAR(255),
   payment_method VARCHAR(255),
   approved BOOLEAN,
   CONSTRAINT pk_order_table PRIMARY KEY (id)
);

CREATE TABLE order_table_bouquets (
  order_id UUID NOT NULL,
   bouquets_uuid UUID NOT NULL
);

ALTER TABLE order_table_bouquets ADD CONSTRAINT fk_ordtabbou_on_bouquet FOREIGN KEY (bouquets_uuid) REFERENCES bouquet (uuid);

ALTER TABLE order_table_bouquets ADD CONSTRAINT fk_ordtabbou_on_order FOREIGN KEY (order_id) REFERENCES order_table (id);