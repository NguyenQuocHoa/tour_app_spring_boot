package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "typePrice")
public class TypePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal price;
    private Boolean isActive;

    @OneToMany(mappedBy = "typePrice")
    @JsonIgnore
    private Set<Tour> tours;

    public TypePrice() {
    }

    public TypePrice(Long id, BigDecimal price, Boolean isActive) {
        this.id = id;
        this.price = price;
        this.isActive = isActive;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePrice typePrice = (TypePrice) o;
        return Objects.equals(id, typePrice.id) && Objects.equals(price, typePrice.price) && Objects.equals(isActive, typePrice.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, isActive);
    }
}
