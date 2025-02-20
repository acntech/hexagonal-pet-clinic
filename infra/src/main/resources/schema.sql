-- Drop existing tables if they exist (useful for development)
DROP TABLE IF EXISTS visits;
DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS owners;

-- Owners Table
CREATE TABLE owners
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    first_name VARCHAR(255)                                       NOT NULL,
    last_name  VARCHAR(255)                                       NOT NULL,
    address    VARCHAR(255)                                       NOT NULL,
    city       VARCHAR(255)                                       NOT NULL,
    email      VARCHAR(254)                                       NOT NULL UNIQUE,
    telephone  VARCHAR(15)                                        NOT NULL UNIQUE
);

-- Pets Table
CREATE TABLE pets
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    identifier VARCHAR(16)                                        NOT NULL UNIQUE,
    name           VARCHAR(255)                                       NOT NULL,
    type           VARCHAR(20)                                        NOT NULL CHECK (type IN
                                                                                      ('BIRD', 'CAT', 'DOG', 'FISH', 'HAMSTER', 'HORSE',
                                                                                       'LIZARD', 'OTHER', 'RABBIT', 'SNAKE', 'TURTLE')),
    breed          VARCHAR(255),
    gender         VARCHAR(10)                                        NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
    birth_date     DATE                                               NOT NULL,
    description    VARCHAR(2048),
    owner_id       BIGINT                                             NOT NULL,
    CONSTRAINT fk_pet_owner FOREIGN KEY (owner_id) REFERENCES owners (id) ON DELETE CASCADE
);

-- Visits Table
CREATE TABLE visits
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    pet_id      BIGINT                                             NOT NULL,
    time        TIMESTAMP(6) WITH TIME ZONE                        NOT NULL,
    description VARCHAR(255)                                       NOT NULL,
    CONSTRAINT fk_visit_pet FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE
);

-- Indexes for better performance
CREATE INDEX idx_owners_email ON owners (email);
CREATE INDEX idx_owners_telephone ON owners (telephone);
CREATE INDEX idx_pets_name ON pets (name);
CREATE INDEX idx_visits_pet ON visits (pet_id);
