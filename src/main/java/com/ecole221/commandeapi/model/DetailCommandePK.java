package com.ecole221.commandeapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
@Getter
@Setter
@Embeddable
public class DetailCommandePK implements Serializable {
    @Column(name = "commande_id")
    private UUID commande_id;

    @Column(name = "produit_id")
    private UUID produit_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailCommandePK that = (DetailCommandePK) o;
        return commande_id.equals(that.commande_id) &&
                produit_id.equals(that.produit_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commande_id, produit_id);
    }
}
