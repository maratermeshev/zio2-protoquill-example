CREATE TABLE person
(
    ID          SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    age         INT,
    insurance_id INT,
    is_insured   boolean   DEFAULT false,
    created_at   timestamp DEFAULT now()
);

INSERT INTO person(name, age, is_insured)
VALUES ('Vlad Dracul', 321, false);
INSERT INTO person(name, age, insurance_id, is_insured)
VALUES ('Joe Bloggs', 20, 1234567890, true);