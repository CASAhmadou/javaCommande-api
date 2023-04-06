package com.ecole221.commandeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Commande> commandes;

    //@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurant_produits",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id"))
    private List<Produit> produits;

}
