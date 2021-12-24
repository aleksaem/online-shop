docker run --name webstore -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pswd -e POSTGRES_DB=online-shop -d postgres

CREATE TABLE products( id SERIAL PRIMARY KEY, name CHAR(50) NOT NULL, price numeric NOT NULL, description CHAR(200) NOT NULL, date date NOT NULL );