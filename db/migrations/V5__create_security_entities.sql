--Users and Authorities

--createTable(users)
CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
   password VARCHAR(500) NOT NULL,
   enabled BOOLEAN NOT NULL,
   CONSTRAINT users_pkey PRIMARY KEY (username)
);

--createTable(authorities)
CREATE TABLE authorities (
  authority VARCHAR(50) NOT NULL,
   username VARCHAR(50) NOT NULL
);

--addForeignKeyConstraint(fk_authorities_users)
ALTER TABLE authorities ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username);

--createIndex(ix_auth_username)
CREATE INDEX ix_auth_username ON authorities(username, authority);

--insert(users#username#admin#password#{bcrypt}$2a$10$HGTok2MzVIq8hHdrdmCeN.2iCFhnpEZRhwwqzn6J8pPNg7ScrE6c.)
INSERT 
INTO
  users
  (username, password, enabled) 
VALUES
  ('admin', '{bcrypt}$2a$10$HGTok2MzVIq8hHdrdmCeN.2iCFhnpEZRhwwqzn6J8pPNg7ScrE6c.', TRUE);

--insert(authorities#authority#ROLE_ADMIN#username#admin)
INSERT 
INTO
  authorities
  (authority, username) 
VALUES
  ('ROLE_ADMIN', 'admin');

--insert(users#username#pkmn_user_app#password#{bcrypt}$2a$10$Uk2GTKoNpjG5nfCi1lPyTuoFZRUdGY5RqhBmMi27dZ.Pss8V4tTxy)
INSERT 
INTO
  users
  (username, password, enabled) 
VALUES
  ('pkmn_user_app', '{bcrypt}$2a$10$Uk2GTKoNpjG5nfCi1lPyTuoFZRUdGY5RqhBmMi27dZ.Pss8V4tTxy', TRUE);

--insert(authorities#authority#ROLE_USER#username#pkmn_user_app)
INSERT 
INTO
  authorities
  (authority, username) 
VALUES
  ('ROLE_USER', 'pkmn_user_app');

