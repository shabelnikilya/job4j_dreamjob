CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created DATETIME
);
CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name_vacancy TEXT,
    name TEXT,
    second_name TEXT
);