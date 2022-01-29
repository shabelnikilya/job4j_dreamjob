CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP
);
CREATE TABLE IF NOT EXISTS candidate (
    id SERIAL PRIMARY KEY,
    name_vacancy TEXT,
    name TEXT,
    second_name TEXT
);
CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY,
	name TEXT,
	email TEXT,
	password TEXT
);