package com.games.online.shop.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.games.online.shop.GamesOnlineShopApp;
import com.games.online.shop.domain.Category;
import com.games.online.shop.domain.Description;
import com.games.online.shop.domain.Game;
import com.games.online.shop.domain.Mechanics;
import com.games.online.shop.domain.ProductStatus;
import com.games.online.shop.repository.GameRepository;
import com.games.online.shop.security.AuthoritiesConstants;
import com.games.online.shop.service.GameService;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GameResource} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.USER)
@SpringBootTest(classes = GamesOnlineShopApp.class)
public class GameResourceIT {
    private static final String DEFAULT_ID = "0";

    private static final String DEFAULT_NAME = "Monopoly";

    private static final Description DEFAULT_DESCRIPTION = new Description();

    static {
        DEFAULT_DESCRIPTION.setId(1L);
        DEFAULT_DESCRIPTION.setText("Default description");
    }

    private static final Long DEFAULT_PRICE = 100L;

    private static final Short DEFAULT_YEAR_PUBLISHED = 2020;

    private static final Short DEFAULT_MINIMUM_PLAYERS = 2;

    private static final Short DEFAULT_MAXIMUM_PLAYERS = 6;

    private static final Byte DEFAULT_MINIMUM_AGE = 10;

    private static final String DEFAULT_SMALL_IMAGE_URL = "http://placehold.it/150x150";

    private static final String DEFAULT_LARGE_IMAGE_URL = "http://placehold.it/700x700";

    private static final Double DEFAULT_USER_RATING = 4.7;

    private static final String DEFAULT_RULES_URL = "http://placehold.it/rules";

    private static final Short DEFAULT_QUANTITY = 5;

    private static final ProductStatus DEFAULT_PRODUCT_STATUS = new ProductStatus();

    static {
        DEFAULT_PRODUCT_STATUS.setId(2L);
        DEFAULT_PRODUCT_STATUS.setStatus("out_of_stock");
    }

    private static final Mechanics DEFAULT_MECHANICS = new Mechanics();

    static {
        DEFAULT_MECHANICS.setId("abcdef");
        DEFAULT_MECHANICS.setName("Default mechanics");
    }

    private static final Category DEFAULT_CATEGORY = new Category();

    static {
        DEFAULT_CATEGORY.setId("ghijkl");
        DEFAULT_CATEGORY.setName("Default category");
    }

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    /**
     * Create a Game.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the Game entity.
     */
    public static Game createEntity() {
        Game game = new Game();
        game.setId(DEFAULT_ID);
        game.setName(DEFAULT_NAME);
        game.setDescription(DEFAULT_DESCRIPTION);
        DEFAULT_DESCRIPTION.setGame(game);
        game.setPrice(DEFAULT_PRICE);
        game.setYearPublished(DEFAULT_YEAR_PUBLISHED);
        game.setMinimumPlayers(DEFAULT_MINIMUM_PLAYERS);
        game.setMaximumPlayers(DEFAULT_MAXIMUM_PLAYERS);
        game.setMinimumAge(DEFAULT_MINIMUM_AGE);
        game.setSmallImageUrl(DEFAULT_SMALL_IMAGE_URL);
        game.setLargeImageUrl(DEFAULT_LARGE_IMAGE_URL);
        game.setUserRating(DEFAULT_USER_RATING);
        game.setRulesUrl(DEFAULT_RULES_URL);
        game.setQuantity(DEFAULT_QUANTITY);
        game.addMechanics(DEFAULT_MECHANICS);
        game.addCategory(DEFAULT_CATEGORY);
        return game;
    }

    @BeforeEach
    public void initTest() {
        game = createEntity();
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Get all the games
        restGameMockMvc
            .perform(get("/api/games?sort=id,asc").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @Transactional
    public void getAllGamesSortedByParameters() throws Exception {
        restGameMockMvc.perform(get("/api/games?sort=description,asc")).andExpect(status().isBadRequest());
        restGameMockMvc.perform(get("/api/games?sort=status,asc")).andExpect(status().isBadRequest());
        restGameMockMvc.perform(get("/api/games?sort=userRating,desc")).andExpect(status().isOk());
    }
}
