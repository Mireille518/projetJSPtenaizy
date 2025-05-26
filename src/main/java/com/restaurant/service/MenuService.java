package com.restaurant.service;

import com.restaurant.model.Menu;
import com.restaurant.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenuItems() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu getMenuByIdplat(String idplat) {
        return menuRepository.findByIdplat(idplat);
    }

    public Menu createMenuItem(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenuItem(Long id, Menu menuDetails) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            Menu existingMenu = menu.get();
            existingMenu.setNomplat(menuDetails.getNomplat());
            existingMenu.setPu(menuDetails.getPu());
            return menuRepository.save(existingMenu);
        }
        return null;
    }

    public void deleteMenuItem(Long id) {
        menuRepository.deleteById(id);
    }

    public List<Menu> searchMenuItems(String keyword) {
        return menuRepository.searchByKeyword(keyword);
    }

    public List<Menu> getTop10MostOrderedDishes() {
        return menuRepository.findTop10MostOrderedDishes();
    }
} 