package com.restaurant.controller;

import com.restaurant.model.Menu;
import com.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Menu> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return menu != null ? ResponseEntity.ok(menu) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Menu> searchMenuItems(@RequestParam String keyword) {
        return menuService.searchMenuItems(keyword);
    }

    @GetMapping("/most-ordered")
    public List<Menu> getTop10MostOrderedDishes() {
        return menuService.getTop10MostOrderedDishes();
    }

    @PostMapping
    public Menu createMenuItem(@RequestBody Menu menu) {
        return menuService.createMenuItem(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenuItem(@PathVariable Long id, @RequestBody Menu menuDetails) {
        Menu updatedMenu = menuService.updateMenuItem(id, menuDetails);
        return updatedMenu != null ? ResponseEntity.ok(updatedMenu) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }
} 