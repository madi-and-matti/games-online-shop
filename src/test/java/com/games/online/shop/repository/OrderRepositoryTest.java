package com.games.online.shop.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.games.online.shop.GamesOnlineShopApp;
import com.games.online.shop.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link OrderRepository}.
 */
@SpringBootTest(classes = GamesOnlineShopApp.class)
@Transactional
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testOrderIsCreated() {
        Order order = new Order();
        order = orderRepository.save(order);
        assertThat(order.getId()).isNotNull();
    }

    @Test
    public void testOrderIsDeleted() {
        Order order = new Order();
        order = orderRepository.save(order);
        orderRepository.deleteById(order.getId());
        Optional<Order> deletedOrder = orderRepository.findById(order.getId());
        assertThat(deletedOrder).isEmpty();
    }

    @Test
    public void getOrderItemsReturnsAListOfOrderItems() {
        Order order = new Order();
        order = orderRepository.save(order);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderItem mockedItem = mock(OrderItem.class);
            orderItemList.add(mockedItem);
        }
        order.setOrderItems(orderItemList);
        List<OrderItem> testList = order.getOrderItems();
        assertThat(testList).isNotEmpty();
        assertThat(testList.size()).isEqualTo(3);
    }

    @Test
    public void testLinkingOrderItemAndOrderAddsOrderItemToList() {
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        OrderItemId orderItemId = new OrderItemId(order.getId(), "y56td93iXw");
        orderItem.setId(orderItemId);
        orderItem.setOrder(order);
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.saveAndFlush(order);
        Optional<Order> orderFromDb = orderRepository.findById(savedOrder.getId());
        List<OrderItem> orderItemList = orderFromDb.get().getOrderItems();
        assertThat(orderItemList).hasSize(1);
    }

    @Test
    public void testRemovingOrderItemDeletesItFromOrderItemListOfOrder() {
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        OrderItemId orderItemId = new OrderItemId(order.getId(), "y56td93iXw");
        orderItem.setId(orderItemId);
        orderItem.setOrder(order);
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.saveAndFlush(order);
        orderItemRepository.saveAndFlush(orderItem);
        orderItemRepository.delete(orderItem);
        savedOrder.getOrderItems().remove(orderItem);
        Optional<Order> orderFromDb = orderRepository.findById(savedOrder.getId());
        List<OrderItem> orderItemList = orderFromDb.get().getOrderItems();
        assertThat(orderItemList).hasSize(0);
    }

    @Test
    public void testDeletingOrderDeletesItsOrderItemsButNotGame() {
        String productId = "y56td93iXw";
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        OrderItemId orderItemId = new OrderItemId(order.getId(), productId);
        orderItem.setId(orderItemId);
        orderItem.setOrder(order);
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.saveAndFlush(order);
        orderItemRepository.saveAndFlush(orderItem);
        orderRepository.delete(savedOrder);
        Optional<OrderItem> orderItemFromDb = orderItemRepository.findById(orderItemId);
        assertThat(orderItemFromDb).isEmpty();
        Optional<Game> game = gameRepository.findById(productId);
        assertThat(game).isPresent();
    }
}
