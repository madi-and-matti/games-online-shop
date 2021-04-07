package com.games.online.shop.repository;

import com.games.online.shop.domain.Order;
import com.games.online.shop.domain.OrderStatus;
import com.games.online.shop.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);

    List<Order> findAllByStatus(OrderStatus status);
}
