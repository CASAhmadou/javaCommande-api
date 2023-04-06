package com.ecole221.commandeapi.helper;

import com.ecole221.commandeapi.exception.CommandeException;
import com.ecole221.commandeapi.model.*;
import com.ecole221.commandeapi.repository.CommandeRepository;
import com.ecole221.commandeapi.repository.ProduitRepository;
import com.ecole221.commandeapi.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CreateCommandeHelper {

    private final ProduitRepository produitRepository;
    private final RestaurantRepository restaurantRepository;
    private final CommandeRepository commandeRepository;

    public CreateCommandeHelper(ProduitRepository produitRepository, RestaurantRepository restaurantRepository, CommandeRepository commandeRepository) {
        this.produitRepository = produitRepository;
        this.restaurantRepository = restaurantRepository;
        this.commandeRepository = commandeRepository;
    }

    public Commande saveCommande(Commande commande){
        processCommand(commande);
        return commandeRepository.save(commande);
    }
    public List<Commande> findAllCommande(){
        return commandeRepository.findAll();
    }

    public void addCommandeToAdresse(Commande commande){
        commande.getAdresseDeLivraison().setCommande(commande);
    }

    private void processCommand(Commande commande){
        initStatut(commande);
        validateRestaurant(commande.getRestaurant());
        List<Produit> produits = commande.getDetailCommandes().stream().map(detail-> detail.getProduit()).toList();
        checkProduitInRestaurant(commande.getRestaurant(), produits);
        //checkProduitIfActiveInRestaurant(commande.getRestaurant(), produits);
        validatePrix(commande);
        addCommandeToAdresse(commande);
        updateCommandeAndProduitToDetailCommandes(commande);
    }

    private void checkProduitIfActiveInRestaurant(Restaurant restaurant, List<Produit> produits) {
        List<String> errorMessages = new ArrayList<>();
        produits.stream().map(produit -> {
            Produit p = produitRepository.findByRestaurantsAndId(restaurant, produit.getId());
            if(p != null && !p.getActive()){
                errorMessages.add("Produit avec id :"+produit.getId()
                        +" n'est pas disponible pour le restaurant avec id : "+restaurant.getId());
            }
            return produit;
        }).toList();
        if(!errorMessages.isEmpty()){
            throw new CommandeException(String.join("; ", errorMessages));
        }
    }

    private void checkProduitInRestaurant(Restaurant restaurant, List<Produit> produits) {
        List<String> errorMessages = new ArrayList<>();
        produits.stream().map(produit -> {
            Produit p = produitRepository.findByRestaurantsAndId(restaurant, produit.getId());
            if(p == null){
                errorMessages.add("Produit avec id :"+produit.getId()
                        +" n'existe pas pour le restaurant avec id : "+restaurant.getId());
            }
            return produit;
        }).toList();
        if(!errorMessages.isEmpty()){
            throw new CommandeException(String.join("; ", errorMessages));
        }
    }

    private void validateRestaurant(Restaurant restaurant){
        Optional<Restaurant> resto = restaurantRepository.findById(restaurant.getId());
        if(resto.isEmpty()){
            log.info("restaurant avec l'id {} introuvable", restaurant.getId());
            throw new CommandeException("restaurant avec l'id "+restaurant.getId()+" introuvable");
        }

        if(!resto.get().isActive()){
            log.info("restaurant avec l'id {} est inactif", restaurant.getId());
            throw new CommandeException("restaurant avec l'id "+restaurant.getId()+" est inactif");
        }

    }

    private void updateCommandeAndProduitToDetailCommandes(Commande commande){
        commande.setDetailCommandes(commande.getDetailCommandes().stream().map(detailCommande -> {
            detailCommande.setCommande(commande);
            return detailCommande;
        }).toList());
    }

    private void initStatut(Commande commande){
        commande.setCommandeStatut(CommandeStatut.ENCOURS);
        log.info("statut en cours pour la commande avec l'id : {}", commande.getCommandeStatut());
    }

    private void validatePrix(Commande commande){
        BigDecimal prix = commande.getDetailCommandes().stream().map(detail -> {
            return detail.getPrix().multiply(BigDecimal.valueOf(detail.getQuantite()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        if(prix != null && prix.compareTo(commande.getPrix()) < 0){
            log.info("prix invalide pour la commande");
            throw new CommandeException("prix invalide pour la commande");
        }
    }

}
