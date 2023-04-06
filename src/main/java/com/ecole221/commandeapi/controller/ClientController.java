package com.ecole221.commandeapi.controller;

import com.ecole221.commandeapi.model.Client;
import com.ecole221.commandeapi.repository.ClientRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/clients", produces = "application/vnd.api.v1+json")
@RestController
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public Client save(@RequestBody Client client){
        return clientRepository.save(client);
    }
}
