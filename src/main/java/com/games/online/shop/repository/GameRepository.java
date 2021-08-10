package com.games.online.shop.repository;

import com.games.online.shop.domain.Game;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Page<Game> findAllByPriceNot(Pageable pageable, Long price);

    List<Game> findAllByMechanicsId(String mechanicsId);

    List<Game> findAllByCategoriesId(String categoryId);
}
