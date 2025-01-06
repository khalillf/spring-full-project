package com.devoir.microservicecommandes.services;

import com.devoir.microservicecommandes.models.Commande;
import com.devoir.microservicecommandes.repositories.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {
    private CommandeRepository commandeRepository;
    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }
    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }
    public Optional<Commande> findById(int id) {
        return commandeRepository.findById(id);
    }
    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
    }
    public void delete(Commande commande) {
        commandeRepository.delete(commande);
    }
    public void delete(int id) {
        commandeRepository.deleteById(id);
    }
    public Commande update(Commande commande) {
        Commande existingCommande = commandeRepository.findById(commande.getId()).orElse(null);
        if (existingCommande != null) {
            existingCommande.setDescription(commande.getDescription());
            existingCommande.setDate(commande.getDate());
            existingCommande.setMontant(commande.getMontant());
            existingCommande.setQuantity(commande.getQuantity());
            return commandeRepository.save(commande);
        }
        return null;
    }
}
