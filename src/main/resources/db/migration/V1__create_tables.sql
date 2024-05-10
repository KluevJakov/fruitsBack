CREATE TABLE Bouquet (
  id UUID NOT NULL,
   CONSTRAINT pk_bouquet PRIMARY KEY (id)
);

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

ALTER TABLE Ingredient ADD CONSTRAINT FK_INGREDIENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES Category (id);

CREATE TABLE order_table (
  id UUID NOT NULL,
   customerName VARCHAR(255),
   phoneCustomer VARCHAR(255),
   email VARCHAR(255),
   phoneReceiver VARCHAR(255),
   delivery VARCHAR(255),
   deliveryDate TIMESTAMP WITHOUT TIME ZONE,
   address VARCHAR(255),
   comment VARCHAR(255),
   paymentMethod VARCHAR(255),
   CONSTRAINT pk_order_table PRIMARY KEY (id)
);

CREATE TABLE order_table_bouquets (
  Order_id UUID NOT NULL,
   bouquets_id UUID NOT NULL
);

CREATE TABLE order_table_ingredients (
  Order_id UUID NOT NULL,
   ingredients_id UUID NOT NULL
);

ALTER TABLE order_table_bouquets ADD CONSTRAINT fk_ordtabbou_on_bouquet FOREIGN KEY (bouquets_id) REFERENCES Bouquet (id);

ALTER TABLE order_table_bouquets ADD CONSTRAINT fk_ordtabbou_on_order FOREIGN KEY (Order_id) REFERENCES order_table (id);

ALTER TABLE order_table_ingredients ADD CONSTRAINT fk_ordtabing_on_ingredient FOREIGN KEY (ingredients_id) REFERENCES Ingredient (id);

ALTER TABLE order_table_ingredients ADD CONSTRAINT fk_ordtabing_on_order FOREIGN KEY (Order_id) REFERENCES order_table (id);