DROP SCHEMA IF EXISTS "commande" CASCADE;
CREATE SCHEMA "commande";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TYPE IF EXISTS commande_statut;

CREATE TYPE commande_statut AS ENUM ('ENCOURS', 'PAYE', 'APPROUVE', 'EN_ANNULATION', 'ANNULE');

DROP TABLE IF EXISTS "commande".commandes CASCADE;
CREATE TABLE "commande".commandes
(
    id uuid default gen_random_uuid() NOT NULL,
    date DATE NOT NULL,
    client_id uuid NOT NULL,
    restaurant_id uuid NOT NULL,
    prix numeric(10,2) NOT NULL,
    commande_statut commande_statut NOT NULL,
    CONSTRAINT commandes_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "commande".clients CASCADE;
CREATE TABLE "commande".clients
(
    id uuid default gen_random_uuid() NOT NULL,
    nom character varying COLLATE pg_catalog."default" NOT NULL,
    prenom character varying COLLATE pg_catalog."default" NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clients_pkey PRIMARY KEY (id)
);

ALTER TABLE "commande".commandes
    ADD CONSTRAINT "FK_CLIENT_ID" FOREIGN KEY (client_id)
        REFERENCES "commande".clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID;


DROP TABLE IF EXISTS "commande".produits CASCADE;
CREATE TABLE "commande".produits
(
    id uuid default gen_random_uuid() NOT NULL,
    nom character varying COLLATE pg_catalog."default" NOT NULL,
    prix numeric(10, 2) NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT produits_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "commande".restaurants CASCADE;
CREATE TABLE "commande".restaurants
(
    id uuid default gen_random_uuid() NOT NULL,
    nom character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT restaurants_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "commande".detailCommandes CASCADE;

CREATE TABLE "commande".detailCommandes
(
    id uuid default gen_random_uuid() NOT NULL,
    commande_id uuid NOT NULL,
    produit_id uuid NOT NULL,
    prix numeric(10,2) NOT NULL,
    quantite integer NOT NULL,
    sous_total numeric(10,2) NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id, commande_id, produit_id)
);

ALTER TABLE "commande".detailCommandes
    ADD CONSTRAINT "FK_COMMANDE_ID" FOREIGN KEY (commande_id)
        REFERENCES "commande".commandes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID;

ALTER TABLE "commande".detailCommandes
    ADD CONSTRAINT "FK_PRODUIT_ID" FOREIGN KEY (produit_id)
        REFERENCES "commande".produits (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID;

DROP TABLE IF EXISTS "commande".adresseDeLivraison CASCADE;

CREATE TABLE "commande".adressedelivraison
(
    id uuid default gen_random_uuid() NOT NULL,
    commande_id uuid UNIQUE NOT NULL,
    rue character varying COLLATE pg_catalog."default" NOT NULL,
    code_postal character varying COLLATE pg_catalog."default" NOT NULL,
    ville character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT order_address_pkey PRIMARY KEY (id, commande_id)
);

ALTER TABLE "commande".adressedelivraison
    ADD CONSTRAINT "FK_COMMANDE_ID" FOREIGN KEY (commande_id)
        REFERENCES "commande".commandes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID;

DROP TABLE IF EXISTS commande.restaurant_produits CASCADE;

CREATE TABLE commande.restaurant_produits
(
    restaurant_id uuid NOT NULL,
    produit_id uuid NOT NULL,
    CONSTRAINT restaurant_products_pkey PRIMARY KEY (restaurant_id, produit_id)
);

ALTER TABLE commande.restaurant_produits
    ADD CONSTRAINT "FK_RESTAURANT_ID" FOREIGN KEY (restaurant_id)
        REFERENCES commande.restaurants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID;

ALTER TABLE commande.restaurant_produits
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (produit_id)
        REFERENCES commande.produits (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID;

DROP TYPE IF EXISTS paiement_statut;

CREATE TYPE paiement_statut AS ENUM ('COMPLET', 'ANNULE', 'ECHEC');

CREATE TABLE "commande".paiements
(
    id uuid default gen_random_uuid() NOT NULL,
    commande_id uuid NOT NULL,
    prix numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    statut paiement_statut NOT NULL,
    CONSTRAINT paiements_pkey PRIMARY KEY (id)
);

ALTER TABLE commande.paiements
    ADD CONSTRAINT "FK_COMMAND_ID" FOREIGN KEY (commande_id)
        REFERENCES commande.commandes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID;

