package com.games.online.shop.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.games.online.shop.GamesOnlineShopApp;
import com.games.online.shop.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link OrderItemRepository}.
 */
@SpringBootTest(classes = GamesOnlineShopApp.class)
@Transactional
public class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testDeletingOrderDeletesItsOrderItems() {
        Order order = new Order();
        order.setId(4L);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        OrderItemId orderItemId = new OrderItemId(order.getId(), "y56td93iXw");
        orderItem.setId(orderItemId);
        order.setOrderItems(orderItemList);
        order = orderRepository.save(order);
        orderRepository.delete(order);
        List<OrderItem> orderItemListFromDb = orderItemRepository.findAllByOrderId(order.getId());
        assertThat(orderItemListFromDb).isEmpty();
    }
}
