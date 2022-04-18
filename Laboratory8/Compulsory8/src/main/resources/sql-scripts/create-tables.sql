-- Database: cities

-- DROP DATABASE IF EXISTS cities;

/*
DROP TABLE IF EXISTS continents;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS city_sister_relation;
*/

CREATE TABLE continents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code CHAR(2),
    continent INTEGER,
	CONSTRAINT fk_continent FOREIGN KEY(continent) REFERENCES continents(id) ON DELETE CASCADE
);

CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
	country INTEGER,
	capital BOOLEAN,
	latitude DOUBLE PRECISION,
	longitude DOUBLE PRECISION,
    CONSTRAINT fk_country FOREIGN KEY(country) REFERENCES countries(id) ON DELETE CASCADE
);

CREATE TABLE city_sister_relation (
	id1 INTEGER,
	id2 INTEGER,
	CONSTRAINT fk_id1 FOREIGN KEY(id1) REFERENCES cities(id) ON DELETE CASCADE,
	CONSTRAINT fk_id2 FOREIGN KEY(id2) REFERENCES cities(id) ON DELETE CASCADE
);

/*
SELECT * FROM continents;
SELECT * FROM countries;
SELECT * FROM cities;
*/