package com.games.online.shop.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "street_address", length = 50)
    private String streetAddress;

    @Size(max = 20)
    @Column(length = 20)
    private String unit;

    @Size(max = 50)
    @Column(length = 50)
    private String city;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private AddressState state;

    @Size(max = 10)
    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressState getState() {
        return state;
    }

    public void setState(AddressState state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return (
            "Address{" +
            "id=" +
            id +
            ", streetAddress='" +
            streetAddress +
            '\'' +
            ", unit='" +
            unit +
            '\'' +
            ", city='" +
            city +
            '\'' +
            ", state=" +
            state +
            ", zipCode='" +
            zipCode +
            '\'' +
            ", user=" +
            user +
            '}'
        );
    }
}
