package com.games.online.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "random_alphanumeric", strategy = "com.games.online.shop.domain.generator.AlphanumericIdGenerator")
    @GeneratedValue(generator = "random_alphanumeric", strategy = GenerationType.IDENTITY)
    private String id;

    @Size(max = 200)
    private String name;

    @Column(name = "year_published")
    private Short yearPublished;

    @Column(name = "min_players")
    private Short minimumPlayers;

    @Column(name = "max_players")
    private Short maximumPlayers;

    @Column(name = "min_age")
    private Byte minimumAge;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private ProductStatus status;

    @Size(max = 300)
    @Column(name = "small_img")
    private String smallImageUrl;

    @Size(max = 300)
    @Column(name = "large_img")
    private String largeImageUrl;

    @Column(name = "user_rating")
    private Double userRating;

    @Size(max = 500)
    @Column(name = "rules_url")
    private String rulesUrl;

    private Short quantity;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    private Description description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "game_mechanics",
        joinColumns = { @JoinColumn(name = "game_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "mechanics_id", referencedColumnName = "id") }
    )
    private Set<Mechanics> mechanics = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "game_category",
        joinColumns = { @JoinColumn(name = "game_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id", referencedColumnName = "id") }
    )
    private Set<Category> categories = new HashSet<>();

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Short yearPublished) {
        this.yearPublished = yearPublished;
    }

    public Short getMinimumPlayers() {
        return minimumPlayers;
    }

    public void setMinimumPlayers(Short minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    public Short getMaximumPlayers() {
        return maximumPlayers;
    }

    public void setMaximumPlayers(Short maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    public Byte getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Byte minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getRulesUrl() {
        return rulesUrl;
    }

    public void setRulesUrl(String rulesUrl) {
        this.rulesUrl = rulesUrl;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Set<Mechanics> getMechanics() {
        return mechanics;
    }

    public void setMechanics(Set<Mechanics> mechanics) {
        this.mechanics = mechanics;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @JsonIgnore
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id.equals(game.id) && Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Game{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
