package com.games.online.shop.repository;

import com.games.online.shop.domain.AddressState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressStateRepository extends JpaRepository<AddressState, Long> {}
