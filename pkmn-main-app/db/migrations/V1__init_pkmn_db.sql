CREATE TABLE cards (
  id UUID NOT NULL,
   stage VARCHAR(255),
   name VARCHAR(255),
   hp INTEGER NOT NULL,
   evolves_from_id UUID,
   attack_skills JSON,
   weakness_type VARCHAR(255),
   resistance_type VARCHAR(255),
   retreat_cost VARCHAR(255),
   game_set VARCHAR(255),
   pokemon_type VARCHAR(255),
   regulation_mark CHAR NOT NULL,
   pokemon_owner_id UUID,
   card_number VARCHAR(255),
   CONSTRAINT pk_cards PRIMARY KEY (id)
);

CREATE TABLE students (
  id UUID NOT NULL,
   first_name VARCHAR(255),
   sur_name VARCHAR(255),
   family_name VARCHAR(255),
   "group" VARCHAR(255),
   CONSTRAINT pk_students PRIMARY KEY (id)
);

ALTER TABLE cards ADD CONSTRAINT FK_CARDS_ON_EVOLVESFROM FOREIGN KEY (evolves_from_id) REFERENCES cards (id);

ALTER TABLE cards ADD CONSTRAINT FK_CARDS_ON_POKEMONOWNER FOREIGN KEY (pokemon_owner_id) REFERENCES students (id);