package com.games.online.shop.service;

import com.games.online.shop.domain.Game;
import com.games.online.shop.repository.GameRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing games.
 */
@Service
@Transactional
public class GameService {
    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional(readOnly = true)
    public Page<Game> getAllGamesWithPrice(Pageable pageable) {
        return gameRepository.findAllByPriceNot(pageable, 0L);
    }

    @Transactional(readOnly = true)
    public Optional<Game> getSingleGame(String gameId) {
        return gameRepository.findById(gameId);
    }
}
