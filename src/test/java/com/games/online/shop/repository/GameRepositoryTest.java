package com.games.online.shop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.games.online.shop.GamesOnlineShopApp;
import com.games.online.shop.domain.Category;
import com.games.online.shop.domain.Description;
import com.games.online.shop.domain.Game;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link GameRepository}.
 */
@SpringBootTest(classes = GamesOnlineShopApp.class)
@Transactional
class GameRepositoryTest {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Test
    public void testAddGameIdGenerator() {
        Game game = new Game();
        game.setName("Test Game");
        assertThat(game.getId()).isNull();
        game = gameRepository.save(game);
        assertThat(game.getId()).isNotNull();
        assertThat(game.getId().length()).isEqualTo(10);
    }

    @Test
    public void testIdGeneratorDoNothingOnUpdate() {
        Optional<Game> optGame = gameRepository.findById("Krn8i8C0fI");
        if (optGame.isPresent()) {
            Game game = optGame.get();
            game.setQuantity((short) 2);
            game = gameRepository.saveAndFlush(game);
            assertThat(game.getId()).isEqualTo("Krn8i8C0fI");
        }
    }

    @Test
    public void testDeleteGameCascading() {
        Game game = new Game();
        game.setName("Test Game");
        Description description = new Description();
        description.setText("Test game description");
        Category category = new Category();
        category.setName("Test Category");

        game.setDescription(description);
        game.addCategory(category);

        gameRepository.saveAndFlush(game);

        Long descriptionId = description.getId();
        assertThat(descriptionId).isNotNull();

        assertThat(description.getGame()).isNotNull();

        String categoryId = category.getId();
        assertThat(categoryId).isNotNull();
        System.out.println("category: " + category);

        gameRepository.delete(game);

        List<Description> foundDescription = descriptionRepository.findAllById(Collections.singletonList(descriptionId));
        assertThat(foundDescription).isEmpty();

        Category foundCategory = categoryRepository.getOne(categoryId);
        assertThat(foundCategory).isNotNull();
    }
}
