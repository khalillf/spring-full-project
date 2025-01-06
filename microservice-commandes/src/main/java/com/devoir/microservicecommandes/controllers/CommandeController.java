package com.devoir.microservicecommandes.controllers;

import com.devoir.microservicecommandes.models.Commande;
import com.devoir.microservicecommandes.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // Get all commandes
    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.findAll();
    }

    // Get commande by ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable int id) {
        Optional<Commande> commande = commandeService.findById(id);
        return commande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new commande
    @PostMapping
    public Commande addCommande(@RequestBody Commande commande) {
        return commandeService.save(commande);
    }

    // Delete a commande by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommandeById(@PathVariable int id) {
        Optional<Commande> commande = commandeService.findById(id);
        if (commande.isPresent()) {
            commandeService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
