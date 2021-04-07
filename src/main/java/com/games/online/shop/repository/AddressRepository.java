package com.games.online.shop.repository;

import com.games.online.shop.domain.Address;
import com.games.online.shop.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);
}
