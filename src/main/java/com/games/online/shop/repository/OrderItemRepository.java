package com.games.online.shop.repository;

import com.games.online.shop.domain.OrderItem;
import com.games.online.shop.domain.OrderItemId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    List<OrderItem> findAllByOrderId(Long orderId);
}
