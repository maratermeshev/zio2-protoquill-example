CREATE TABLE person
(
    ID          SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    age         INT,
    insuranceId INT,
    isInsured   boolean   DEFAULT false,
    createdAt   timestamp DEFAULT now()
);

INSERT INTO person(name, age, isInsured)
VALUES ('Vlad Dracul', 321, false);
INSERT INTO person(name, age, insuranceId, isInsured)
VALUES ('Joe Bloggs', 20, 1234567890, true);
