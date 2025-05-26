package com.restaurant.service;

import com.restaurant.model.Table;
import com.restaurant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public Table getTableById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    public Table getTableByIdtable(String idtable) {
        return tableRepository.findByIdtable(idtable);
    }

    public Table createTable(Table table) {
        return tableRepository.save(table);
    }

    public Table updateTable(Long id, Table tableDetails) {
        Optional<Table> table = tableRepository.findById(id);
        if (table.isPresent()) {
            Table existingTable = table.get();
            existingTable.setDesignation(tableDetails.getDesignation());
            existingTable.setOccupation(tableDetails.isOccupation());
            return tableRepository.save(existingTable);
        }
        return null;
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
} 