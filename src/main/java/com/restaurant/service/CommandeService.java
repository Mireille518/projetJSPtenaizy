package com.restaurant.service;

import com.restaurant.model.Commande;
import com.restaurant.repository.CommandeRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public Commande createCommande(Commande commande) {
        commande.setDatecom(new Date());
        return commandeRepository.save(commande);
    }

    public Commande updateCommande(Long id, Commande commandeDetails) {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            Commande existingCommande = commande.get();
            existingCommande.setMenu(commandeDetails.getMenu());
            existingCommande.setNomcli(commandeDetails.getNomcli());
            existingCommande.setTypecom(commandeDetails.getTypecom());
            existingCommande.setTable(commandeDetails.getTable());
            existingCommande.setQuantite(commandeDetails.getQuantite());
            return commandeRepository.save(existingCommande);
        }
        return null;
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    public List<Commande> getCommandesBetweenDates(Date startDate, Date endDate) {
        return commandeRepository.findByDatecomBetween(startDate, endDate);
    }

    public List<Commande> getClientOrdersBetweenDates(int clientId, Date startDate, Date endDate) {
        return commandeRepository.findClientOrdersBetweenDates(clientId, startDate, endDate);
    }

    public Double calculateTotalRevenue(Date startDate, Date endDate) {
        return commandeRepository.calculateTotalRevenueBetweenDates(startDate, endDate);
    }

    public List<Object[]> getLastSixMonthsRevenue() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        return commandeRepository.getMonthlyRevenue(cal.getTime());
    }

    public void generateBillPDF(Commande commande, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Add restaurant name
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("RESTAURANT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add order details
            document.add(new Paragraph("\nCode Commande: " + commande.getIdcom()));
            document.add(new Paragraph("Client: " + commande.getNomcli()));
            if (commande.getTable() != null) {
                document.add(new Paragraph("Table: " + commande.getTable().getIdtable()));
            }
            document.add(new Paragraph("\nDétails de la commande:"));
            
            // Add menu item details
            document.add(new Paragraph(String.format("\n%-20s %10s %10s %15s",
                "Plat", "Prix", "Quantité", "Total")));
            document.add(new Paragraph("------------------------------------------------"));
            
            double total = commande.getMenu().getPu() * commande.getQuantite();
            document.add(new Paragraph(String.format("%-20s %10.2f %10d %15.2f",
                commande.getMenu().getNomplat(),
                commande.getMenu().getPu(),
                commande.getQuantite(),
                total)));

            document.add(new Paragraph("\nTOTAL: " + total + " Ariary"));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }
} 