INSERT INTO commande.restaurants(id, nom, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'restaurant_1', TRUE);
INSERT INTO commande.restaurants(id, nom, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'restaurant_2', FALSE);

INSERT INTO commande.produits(id, nom, prix, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb47', 'product_1', 25.00, FALSE);
INSERT INTO commande.produits(id, nom, prix, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb48', 'product_2', 50.00, TRUE);
INSERT INTO commande.produits(id, nom, prix, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb49', 'product_3', 20.00, FALSE);
INSERT INTO commande.produits(id, nom, prix, active)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb50', 'product_4', 40.00, TRUE);

INSERT INTO commande.restaurant_produits(restaurant_id, produit_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb47');
INSERT INTO commande.restaurant_produits(restaurant_id, produit_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb48');
INSERT INTO commande.restaurant_produits(restaurant_id, produit_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb49');
INSERT INTO commande.restaurant_produits(restaurant_id, produit_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb50');
