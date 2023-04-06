package com.ecole221.commandeapi.controller;

import com.ecole221.commandeapi.exception.CommandeException;
import com.ecole221.commandeapi.helper.CreateCommandeHelper;
import com.ecole221.commandeapi.model.Commande;
import com.ecole221.commandeapi.repository.CommandeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/commandes", produces = "application/vnd.api.v1+json")
@RestController
public class CommandeController {
    private final CreateCommandeHelper createCommandeHelper;

    public CommandeController(CreateCommandeHelper createCommandeHelper) {
        this.createCommandeHelper = createCommandeHelper;
    }

    @PostMapping
    public Commande save(@RequestBody Commande commande){
        try {
            return createCommandeHelper.saveCommande(commande);
        } catch (Exception e) {
            throw new CommandeException("Erreur :"+e);
        }
    }

    @GetMapping
    public List<Commande> allCommandes(){
        try {
            return createCommandeHelper.findAllCommande();
        } catch (Exception e) {
            throw new CommandeException("Erreur :"+e);
        }
    }
}
