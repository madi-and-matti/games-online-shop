package com.games.online.shop.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "shop_order")
public class Order extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "primary_id")
    private SpringSession session;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    @Column(name = "price_total")
    private Long priceTotal;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SpringSession getSession() {
        return session;
    }

    public void setSession(SpringSession session) {
        this.session = session;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Long priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    @Override
    public String toString() {
        return (
            "Order{" +
            "id=" +
            id +
            ", user=" +
            user +
            ", session=" +
            session +
            ", status=" +
            status +
            ", priceTotal=" +
            priceTotal +
            ", address=" +
            address +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id.equals(order.id) && user.equals(order.user);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
