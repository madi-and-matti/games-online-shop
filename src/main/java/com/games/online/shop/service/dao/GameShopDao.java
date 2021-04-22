package com.games.online.shop.service.dao;

import com.games.online.shop.domain.Category;
import com.games.online.shop.domain.Game;
import com.games.online.shop.domain.Mechanics;
import com.games.online.shop.domain.Order;
import com.games.online.shop.domain.OrderItem;
import com.games.online.shop.domain.OrderItemId;
import com.games.online.shop.repository.CategoryRepository;
import com.games.online.shop.repository.GameRepository;
import com.games.online.shop.repository.MechanicsRepository;
import com.games.online.shop.repository.OrderItemRepository;
import com.games.online.shop.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameShopDao {
    private final Logger log = LoggerFactory.getLogger(GameShopDao.class);

    private GameRepository gameRepository;

    private CategoryRepository categoryRepository;

    private MechanicsRepository mechanicsRepository;

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    public GameShopDao(
        GameRepository gameRepository,
        CategoryRepository categoryRepository,
        MechanicsRepository mechanicsRepository,
        OrderRepository orderRepository,
        OrderItemRepository orderItemRepository
    ) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void deleteCategory(Category category) {
        try {
            List<Game> allGamesWithCategory = gameRepository.findAllByCategoriesId(category.getId());
            allGamesWithCategory.forEach(game -> game.removeCategory(category));
            categoryRepository.delete(category);
        } catch (Exception ex) {
            log.error("Error deleting category categoryId={}", category.getId(), ex);
        }
    }

    public void deleteMechanics(Mechanics mechanics) {
        try {
            List<Game> allGamesWithMechanics = gameRepository.findAllByMechanicsId(mechanics.getId());
            allGamesWithMechanics.forEach(game -> game.removeMechanics(mechanics));
            mechanicsRepository.delete(mechanics);
        } catch (Exception ex) {
            log.error("Error deleting mechanics mechanicsId={}", mechanics.getId(), ex);
        }
    }

    public void addOrderItem(Long orderId, OrderItem orderItem) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                setOrderItemId(orderId, orderItem);
                orderItem.setOrder(order.get());
                order.get().addOrderItem(orderItem);
                orderRepository.save(order.get());
                orderItemRepository.save(orderItem);
            } else {
                throw new RuntimeException("Issue retrieving order");
            }
        } catch (Exception ex) {
            log.error("Error adding order items to order={}", orderId, ex);
        }
    }

    public void deleteOrderItem(Long orderId, OrderItem orderItem) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                order.get().getOrderItems().remove(orderItem);
                orderRepository.save(order.get());
                orderItemRepository.delete(orderItem);
            } else {
                throw new RuntimeException("Issue retrieving order");
            }
        } catch (Exception ex) {
            log.error("Error removing order item for order={}", orderId, ex);
        }
    }

    private void setOrderItemId(Long orderId, OrderItem orderItem) {
        try {
            if (orderItem.getGame() != null) {
                OrderItemId orderItemId = new OrderItemId(orderId, orderItem.getGame().getId());
                orderItem.setId(orderItemId);
            } else {
                throw new NullPointerException("Game for orderItem is null");
            }
        } catch (Exception ex) {
            log.error("Error setting orderItemId for order={}", orderId, ex);
        }
    }
}
