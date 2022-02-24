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
    private String code;
    private BigDecimal price;
    private Boolean isActive;

    @OneToMany(mappedBy = "typePrice")
    @JsonIgnore
    private Set<Tour> tours;

    public TypePrice() {
    }

    public TypePrice(Long id, String code, BigDecimal price, Boolean isActive) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePrice typePrice = (TypePrice) o;
        return Objects.equals(id, typePrice.id) && Objects.equals(code, typePrice.code) && Objects.equals(price, typePrice.price) && Objects.equals(isActive, typePrice.isActive) && Objects.equals(tours, typePrice.tours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, price, isActive, tours);
    }
}
