package com.games.online.shop.service.dao;

import com.games.online.shop.repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameShopDao {
    private GameRepository gameRepository;

    public GameShopDao(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
