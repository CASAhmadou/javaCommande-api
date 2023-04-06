package com.ecole221.commandeapi.repository;

import com.ecole221.commandeapi.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, UUID> {
}
