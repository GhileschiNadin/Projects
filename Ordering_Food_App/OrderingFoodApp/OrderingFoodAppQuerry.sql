/* -- Create Customers Table
CREATE TABLE Customers (
    customer_id INT auto_increment PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
	pass_customer VARCHAR(20) NOT NULL
);

-- Create Restaurants Table
CREATE TABLE Restaurants (
    restaurant_id INT auto_increment PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

-- Create Items Table
CREATE TABLE Items (
    item_id INT auto_increment PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    restaurant_id INT,
    Constraint fk_restaurant
    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id)
);

-- Create Orders Table
CREATE TABLE Orders (
    order_id INT auto_increment PRIMARY KEY,
    customer_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    Constraint fk_customer 
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
       Constraint fk_restaurant2
    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id)
);

-- Create OrderItems Table
CREATE TABLE OrderItems (
    order_item_id INT auto_increment PRIMARY KEY, 
    order_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
       Constraint fk_order
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
       Constraint fk_item
    FOREIGN KEY (item_id) REFERENCES Items(item_id)
); */

/*  -- Insert some sample data into the Customers table
INSERT INTO Customers (name, address, phone_number, pass_customer)
VALUES 
    ('PopescuP', 'Zorilor 21', '0773 -826-540', 'password12'),
    ('MangaD', 'Bucegi 15', '0773 -826-541', 'password34'),
    ('LupanN', 'Republicii 5', '0773 -826-542', 'password56'),
	('MorarA', 'Republicii 18', '0773 -826-543', 'password78');

-- Insert some sample data into the Restaurants table
INSERT INTO Restaurants (name, address, phone_number)
VALUES 
    ('McDonalds', 'Mihai Viteazu', '0773 -826-544'),
    ('Sushi Palace', 'Piezisa', '0773 -826-545'),
    ('Noodle Pack', 'VIVO', '0773 -826-546'),
	('Pizza Hot', 'VIVO', '0773 -826-547'),
	('Indian House', 'Marasti', '0773 -826-548'),
	('Mama Manu', 'Parang', '0773 -826-549');


	-- Insert some sample data into the Items table
INSERT INTO Items (name, description, price, restaurant_id)
VALUES 
    ('Margherita Pizza', 'Tomato sauce, mozzarella cheese, and fresh basil', 25.99, 4),
    ('Spicy Tuna Roll', 'Sushi roll with spicy tuna, avocado, and cucumber', 14.99, 2),
    ('Bacon Cheeseburger', 'Grilled beef patty with bacon and cheddar cheese', 15.99, 1),
    ('Chicken Tikka Masala', 'Indian curry dish with marinated chicken and spices', 29.99, 5),
    ('Veggie Wrap', 'Vegetarian wrap with hummus, roasted veggies, and feta cheese', 15.99, 6),
    ('Pepperoni Pizza', 'Tomato sauce, mozzarella cheese, and pepperoni', 24.99, 4),
    ('California Roll', 'Sushi roll with crab, avocado, and cucumber', 17.99, 2),
    ('Double Cheeseburger', 'Two beef patties with American cheese', 25.99, 1);  /*

  
 /* select * from Customers;
  select * from Items;
  select * from Restaurants; */
  