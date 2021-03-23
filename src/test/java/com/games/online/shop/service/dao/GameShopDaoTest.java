package com.games.online.shop.service.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.games.online.shop.GamesOnlineShopApp;
import com.games.online.shop.domain.Category;
import com.games.online.shop.domain.Game;
import com.games.online.shop.domain.Mechanics;
import com.games.online.shop.repository.CategoryRepository;
import com.games.online.shop.repository.GameRepository;
import com.games.online.shop.repository.MechanicsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Integration tests for {@link GameShopDao}.
 */
@SpringBootTest(classes = GamesOnlineShopApp.class)
@Transactional
public class GameShopDaoTest {
    @Autowired
    private GameShopDao gameShopDao;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MechanicsRepository mechanicsRepository;

    @Test
    public void testDeleteCategoryCascading() {
        Category category1 = createCategory("Test category");
        Category category2 = createCategory("Second test category");

        Game game = createGame("Test Game", Arrays.asList(category1, category2), null);

        String categoryId1 = category1.getId();
        String categoryId2 = category2.getId();
        String gameId = game.getId();

        assertThat(categoryId1).isNotNull();
        assertThat(categoryId2).isNotNull();
        assertThat(gameId).isNotNull();
        assertThat(game.getCategories()).hasSize(2);

        gameShopDao.deleteCategory(category1);

        List<Category> foundCategories = categoryRepository.findAllById(new ArrayList<>(Arrays.asList(categoryId1, categoryId2)));
        assertThat(foundCategories).hasSize(1);

        Game foundGame = gameRepository.getOne(gameId);
        assertThat(foundGame).isNotNull();
        assertThat(foundGame.getCategories()).hasSize(1);
    }

    @Test
    public void testDeleteMechanicsCascading() {
        Mechanics mechanics1 = createMechanics("Test mechanics");
        Mechanics mechanics2 = createMechanics("Second test mechanics");

        Game game = createGame("Test Game", null, Arrays.asList(mechanics1, mechanics2));

        String mechanicsId1 = mechanics1.getId();
        String mechanicsId2 = mechanics2.getId();
        String gameId = game.getId();

        assertThat(mechanicsId1).isNotNull();
        assertThat(mechanicsId2).isNotNull();
        assertThat(gameId).isNotNull();
        assertThat(game.getMechanics()).hasSize(2);

        gameShopDao.deleteMechanics(mechanics1);

        List<Mechanics> foundMechanics = mechanicsRepository.findAllById(new ArrayList<>(Arrays.asList(mechanicsId1, mechanicsId2)));
        assertThat(foundMechanics).hasSize(1);

        Game foundGame = gameRepository.getOne(gameId);
        assertThat(foundGame).isNotNull();
        assertThat(foundGame.getMechanics()).hasSize(1);
    }

    private Game createGame(String name, List<Category> categories, List<Mechanics> mechanics) {
        Game game = new Game();
        game.setName(name);
        if (!CollectionUtils.isEmpty(categories)) {
            categories.forEach(game::addCategory);
        }
        if (!CollectionUtils.isEmpty(mechanics)) {
            mechanics.forEach(game::addMechanics);
        }
        gameRepository.saveAndFlush(game);

        return game;
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.saveAndFlush(category);

        return category;
    }

    private Mechanics createMechanics(String name) {
        Mechanics mechanics = new Mechanics();
        mechanics.setName(name);
        mechanicsRepository.saveAndFlush(mechanics);

        return mechanics;
    }
}
