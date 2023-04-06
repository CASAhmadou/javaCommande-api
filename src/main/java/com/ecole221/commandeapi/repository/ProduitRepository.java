package com.ecole221.commandeapi.repository;

import com.ecole221.commandeapi.model.Produit;
import com.ecole221.commandeapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProduitRepository extends JpaRepository<Produit, UUID> {
    public Produit findByRestaurantsAndId(Restaurant restaurant, UUID id);
}
