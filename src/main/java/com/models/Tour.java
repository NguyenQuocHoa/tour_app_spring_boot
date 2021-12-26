package com.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tour")
public class Tour implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;
    private String image;
    private String note;
    private long creator;
    private Date created;

    public Tour() {
    }

    public Tour(Long id, String code, BigDecimal priceAdult, BigDecimal priceChild, String image, String note) {
        this.id = id;
        this.code = code;
        this.priceAdult = priceAdult;
        this.priceChild = priceChild;
        this.image = image;
        this.note = note;
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

    public long getId() {
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

    @Override
    public int hashCode() {
        return Objects.hash(id, code, priceAdult, priceChild, image, note);
    }
}
