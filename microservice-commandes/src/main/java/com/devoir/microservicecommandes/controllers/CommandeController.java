package com.devoir.microservicecommandes.controllers;

import com.devoir.microservicecommandes.models.Commande;
import com.devoir.microservicecommandes.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private static final Logger logger = LoggerFactory.getLogger(CommandeController.class);

    private final CommandeRepository commandeRepository;

    @Value("${mes-config-ms.commandes-last:2}") // Default value of 30 if property is not found
    private int commandesLast;

    public CommandeController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
        logger.info("Commandes last: {}", commandesLast);
    }

    @GetMapping
    public List<Commande> getLastCommandes() {
        Date date = new Date(System.currentTimeMillis() - commandesLast * 24 * 60 * 60 * 1000L);
        return commandeRepository.findByDateAfter(date);
    }

    @GetMapping("/{id}")
    public Optional<Commande> getCommandeById(@PathVariable long id){
        return commandeRepository.findById(id);
    }
    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        commande.setDate(new Date());
        return commandeRepository.save(commande);
    }

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Commande not found"));
        commande.setDescription(commandeDetails.getDescription());
        commande.setQuantite(commandeDetails.getQuantite());
        commande.setMontant(commandeDetails.getMontant());
        return commandeRepository.save(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeRepository.deleteById(id);
    }
}