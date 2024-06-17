CREATE TABLE bouquet (
  id UUID NOT NULL,
   name VARCHAR(255),
   img VARCHAR(255),
   image_uuid VARCHAR(255),
   quantity INTEGER,
   description VARCHAR(255),
   section VARCHAR(255),
   price VARCHAR(255),
   is_default BOOLEAN,
   is_new BOOLEAN,
   category_id UUID,
   CONSTRAINT pk_bouquet PRIMARY KEY (id)
);

CREATE TABLE bouquet_composition (
  bouquet_id UUID NOT NULL,
   composition_id UUID NOT NULL
);

CREATE TABLE category (
  id UUID NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE image (
  id UUID NOT NULL,
   data OID,
   mime_type VARCHAR(255),
   CONSTRAINT pk_image PRIMARY KEY (id)
);

CREATE TABLE ingredient (
  id UUID NOT NULL,
   name VARCHAR(255),
   translate VARCHAR(255),
   category_id UUID,
   CONSTRAINT pk_ingredient PRIMARY KEY (id)
);

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
   bouquets_id UUID NOT NULL
);

CREATE TABLE roles (
  id UUID NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users (
  id UUID NOT NULL,
   login VARCHAR(255),
   password VARCHAR(255),
   role_id UUID,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE order_table_bouquets ADD CONSTRAINT fk_ordtabbou_on_bouquet FOREIGN KEY (bouquets_id) REFERENCES bouquet (id);

ALTER TABLE order_table_bouquets ADD CONSTRAINT fk_ordtabbou_on_order FOREIGN KEY (order_id) REFERENCES order_table (id);

ALTER TABLE ingredient ADD CONSTRAINT FK_INGREDIENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE bouquet ADD CONSTRAINT FK_BOUQUET_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE bouquet_composition ADD CONSTRAINT fk_boucom_on_bouquet FOREIGN KEY (bouquet_id) REFERENCES bouquet (id);

ALTER TABLE bouquet_composition ADD CONSTRAINT fk_boucom_on_ingredient FOREIGN KEY (composition_id) REFERENCES ingredient (id);