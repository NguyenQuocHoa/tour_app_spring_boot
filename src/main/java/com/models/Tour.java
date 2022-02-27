package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tour")
public class Tour implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;
    private String image;
    @Column(length = 1000)
    private String note;
    private Boolean isActive;
    private Long creator;
    private Date created;

    @OneToMany(mappedBy = "tour")
    @JsonIgnore
    private Set<OrderDetail> orderDetails;

    public Tour() {
    }

    public Tour(String code, BigDecimal priceAdult, BigDecimal priceChild, String image, String note, Boolean isActive, Long creator, Date created) {
        this.code = code;
        this.priceAdult = priceAdult;
        this.priceChild = priceChild;
        this.image = image;
        this.note = note;
        this.isActive = isActive;
        this.creator = creator;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", priceAdult=" + priceAdult +
                ", priceChild=" + priceChild +
                ", image='" + image + '\'' +
                ", note='" + note + '\'' +
                ", creator=" + creator +
                ", created=" + created +
                '}';
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

    public BigDecimal getPriceAdult() {
        return priceAdult;
    }

    public void setPriceAdult(BigDecimal priceAdult) {
        this.priceAdult = priceAdult;
    }

    public BigDecimal getPriceChild() {
        return priceChild;
    }

    public void setPriceChild(BigDecimal priceChild) {
        this.priceChild = priceChild;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id) && Objects.equals(code, tour.code) && Objects.equals(priceAdult, tour.priceAdult) && Objects.equals(priceChild, tour.priceChild) && Objects.equals(image, tour.image) && Objects.equals(note, tour.note) && Objects.equals(isActive, tour.isActive) && Objects.equals(creator, tour.creator) && Objects.equals(created, tour.created) && Objects.equals(orderDetails, tour.orderDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, priceAdult, priceChild, image, note, isActive, creator, created, orderDetails);
    }
}
