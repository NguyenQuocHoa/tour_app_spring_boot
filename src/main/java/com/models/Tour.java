package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String image;
    @Column(length = 1000)
    private String note;
    private Boolean isActive;
    private Long creator;
    private Date created;

    @OneToMany(mappedBy = "tour")
    @JsonIgnore
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "typePrice_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private TypePrice typePrice;

    public Tour() {
    }

    public Tour(Long id, String code, String image, String note, Boolean isActive, Long creator, Date created) {
        this.id = id;
        this.code = code;
        this.image = image;
        this.note = note;
        this.isActive = isActive;
        this.creator = creator;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
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
        return Objects.equals(id, tour.id) && Objects.equals(code, tour.code) && Objects.equals(image, tour.image) && Objects.equals(note, tour.note) && Objects.equals(isActive, tour.isActive) && Objects.equals(creator, tour.creator) && Objects.equals(created, tour.created) && Objects.equals(orderDetails, tour.orderDetails) && Objects.equals(typePrice, tour.typePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, image, note, isActive, creator, created, orderDetails, typePrice);
    }
}
