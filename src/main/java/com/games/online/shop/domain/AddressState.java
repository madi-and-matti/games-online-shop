package com.games.online.shop.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address_state")
public class AddressState implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 20)
    @Column(name = "state", length = 20)
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressState)) return false;
        AddressState that = (AddressState) o;
        return id.equals(that.id) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state);
    }

    @Override
    public String toString() {
        return "AddressState{" + "id=" + id + ", state='" + state + '\'' + '}';
    }
}
