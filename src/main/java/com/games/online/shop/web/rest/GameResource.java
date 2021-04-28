package com.games.online.shop.web.rest;

import com.games.online.shop.domain.Game;
import com.games.online.shop.service.GameService;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing games.
 * <p>
 * This class accesses the {@link Game} entity, and needs to fetch its description, categories, and mechanics.
 */
@RestController
@RequestMapping("/api")
public class GameResource {
    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "name", "price", "quantity")
    );

    private final Logger log = LoggerFactory.getLogger(GameResource.class);

    private final GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * {@code GET /games} : get all games.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all games.
     */
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<Game> page = gameService.getAllGames(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<Game> getSingleGame(@PathVariable String gameId) {
        Optional<Game> game = gameService.getSingleGame(gameId);
        //TODO: How do you notify the client on the frontend that the game was not found?
        return game.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
