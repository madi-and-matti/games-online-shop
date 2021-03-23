package com.games.online.shop.service.dao;

import com.games.online.shop.domain.Category;
import com.games.online.shop.domain.Game;
import com.games.online.shop.domain.Mechanics;
import com.games.online.shop.repository.CategoryRepository;
import com.games.online.shop.repository.GameRepository;
import com.games.online.shop.repository.MechanicsRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameShopDao {
    private final Logger log = LoggerFactory.getLogger(GameShopDao.class);

    private GameRepository gameRepository;

    private CategoryRepository categoryRepository;

    private MechanicsRepository mechanicsRepository;

    public GameShopDao(GameRepository gameRepository, CategoryRepository categoryRepository, MechanicsRepository mechanicsRepository) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.mechanicsRepository = mechanicsRepository;
    }

    public void deleteCategory(Category category) {
        try {
            List<Game> allGamesWithCategory = gameRepository.findAllByCategoriesId(category.getId());
            allGamesWithCategory.forEach(game -> game.removeCategory(category));
            categoryRepository.delete(category);
        } catch (Exception ex) {
            log.error("Error deleting category categoryId={}", category.getId(), ex);
        }
    }

    public void deleteMechanics(Mechanics mechanics) {
        try {
            List<Game> allGamesWithMechanics = gameRepository.findAllByMechanicsId(mechanics.getId());
            allGamesWithMechanics.forEach(game -> game.removeMechanics(mechanics));
            mechanicsRepository.delete(mechanics);
        } catch (Exception ex) {
            log.error("Error deleting mechanics mechanicsId={}", mechanics.getId(), ex);
        }
    }
}
