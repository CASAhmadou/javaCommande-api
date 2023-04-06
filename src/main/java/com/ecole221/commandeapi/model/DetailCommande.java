package com.ecole221.commandeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detailcommandes")
public class DetailCommande  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private BigDecimal prix;
    private Integer quantite;
    @Column(name = "sous_total")
    private BigDecimal sousTotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailCommande that = (DetailCommande) o;
        return commande.equals(that.commande) &&
                produit.equals(that.produit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commande, produit);
    }
}
