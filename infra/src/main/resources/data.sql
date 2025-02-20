-- Insert test owners
INSERT INTO owners (created_at, updated_at, address, city, email, first_name, last_name, telephone)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123 Maple Street', 'Springfield', 'john.doe@email.com', 'John', 'Doe', '555-1234'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '456 Oak Avenue', 'Greendale', 'jane.smith@email.com', 'Jane', 'Smith', '555-5678'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '789 Pine Road', 'Rivertown', 'alice.johnson@email.com', 'Alice', 'Johnson', '555-9876');

-- Insert test pets
INSERT INTO pets (created_at, updated_at, birth_date, description, name, identifier, type, gender, breed, owner_id)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2020-06-15', 'Very playful', 'Buddy',
     '2020061511234568', 'DOG', 'MALE', 'Golden Retriever', (SELECT id FROM owners WHERE email='john.doe@email.com')),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2018-09-22', 'Persian cat, loves sleeping', 'Mittens',
     '2018092212345670', 'CAT', 'FEMALE', 'Persian', (SELECT id FROM owners WHERE email='jane.smith@email.com')),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2019-11-10', 'A curious and friendly rabbit', 'Hopper',
     '2019111013456781', 'RABBIT', 'MALE', 'Netherland Dwarf', (SELECT id FROM owners WHERE email='alice.johnson@email.com'));



-- Insert test visits linked to pets
INSERT INTO visits (created_at, updated_at, description, time, pet_id)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Routine check-up', CURRENT_TIMESTAMP,
     (SELECT id FROM pets WHERE name='Buddy')),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Vaccination', CURRENT_TIMESTAMP,
     (SELECT id FROM pets WHERE name='Mittens')),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dental cleaning', CURRENT_TIMESTAMP,
     (SELECT id FROM pets WHERE name='Hopper'));
