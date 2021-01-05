package com.games.online.shop.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("order_id")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Game game;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Short quantity;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderItem{" + "price=" + price + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return id.equals(orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Order getOrder() {
        return order;
    }

    public Order setOrder(Order order) {
        this.order = order;
        return order;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
