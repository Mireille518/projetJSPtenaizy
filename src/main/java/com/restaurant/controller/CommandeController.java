package com.restaurant.controller;

import com.restaurant.model.Commande;
import com.restaurant.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Commande commande = commandeService.getCommandeById(id);
        return commande != null ? ResponseEntity.ok(commande) : ResponseEntity.notFound().build();
    }

    @GetMapping("/between")
    public List<Commande> getCommandesBetweenDates(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return commandeService.getCommandesBetweenDates(startDate, endDate);
    }

    @GetMapping("/client/{clientId}/between")
    public List<Commande> getClientOrdersBetweenDates(
            @PathVariable int clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return commandeService.getClientOrdersBetweenDates(clientId, startDate, endDate);
    }

    @GetMapping("/revenue/between")
    public ResponseEntity<Double> getTotalRevenue(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Double revenue = commandeService.calculateTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/revenue/last-six-months")
    public ResponseEntity<List<Object[]>> getLastSixMonthsRevenue() {
        List<Object[]> revenue = commandeService.getLastSixMonthsRevenue();
        return ResponseEntity.ok(revenue);
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeService.createCommande(commande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        Commande updatedCommande = commandeService.updateCommande(id, commandeDetails);
        return updatedCommande != null ? ResponseEntity.ok(updatedCommande) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/generate-bill")
    public ResponseEntity<String> generateBill(@PathVariable Long id) {
        try {
            Commande commande = commandeService.getCommandeById(id);
            if (commande == null) {
                return ResponseEntity.notFound().build();
            }
            
            String outputPath = "bills/bill_" + commande.getIdcom() + ".pdf";
            commandeService.generateBillPDF(commande, outputPath);
            return ResponseEntity.ok("Bill generated successfully at: " + outputPath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating bill: " + e.getMessage());
        }
    }
} 