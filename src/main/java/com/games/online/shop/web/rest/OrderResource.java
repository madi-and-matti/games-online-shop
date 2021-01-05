package com.games.online.shop.web.rest;

import com.games.online.shop.domain.Order;
import com.games.online.shop.domain.User;
import com.games.online.shop.repository.OrderItemRepository;
import com.games.online.shop.repository.OrderRepository;
import com.games.online.shop.repository.UserRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderResource {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;

    public OrderResource(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }
}
