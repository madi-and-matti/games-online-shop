package com.games.online.shop.repository;

import com.games.online.shop.domain.SpringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringSessionRepository extends JpaRepository<SpringSession, String> {}
