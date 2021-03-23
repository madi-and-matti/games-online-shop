package com.games.online.shop.repository;

import com.games.online.shop.domain.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {}
