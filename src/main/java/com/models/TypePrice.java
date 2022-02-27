package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "typePrice")
public class TypePrice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private BigDecimal price;
    private Boolean isActive;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "tour_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    public TypePrice() {
    }

    public TypePrice(Long id, String code, BigDecimal price, Boolean isActive) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.isActive = isActive;
    }

    public TypePrice(Long id, String code, BigDecimal price, Boolean isActive, Tour tour) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.isActive = isActive;
        this.tour = tour;
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

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
