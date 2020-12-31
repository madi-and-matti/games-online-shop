package com.games.online.shop.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Mechanics implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "random_alphanumeric", strategy = "com.games.online.shop.domain.generator.AlphanumericIdGenerator")
    @GeneratedValue(generator = "random_alphanumeric", strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mechanics mechanics = (Mechanics) o;
        return id.equals(mechanics.id) && name.equals(mechanics.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Mechanics{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
