package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    @Column(length = 1000)
    private String note;
    private Integer numberAdult;
    private Integer numberChild;
    private BigDecimal totalAmount;
    private Boolean isSuccess;
    private Boolean isPayOnline;
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<OrderDetail> orderDetails;

    public Order() {
    }

    public Order(Long id, String code, String note, Integer numberAdult, Integer numberChild, BigDecimal totalAmount, Boolean isSuccess, Boolean isPayOnline) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.numberAdult = numberAdult;
        this.numberChild = numberChild;
        this.totalAmount = totalAmount;
        this.isSuccess = isSuccess;
        this.isPayOnline = isPayOnline;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getNumberAdult() {
        return numberAdult;
    }

    public void setNumberAdult(Integer numberAdult) {
        this.numberAdult = numberAdult;
    }

    public Integer getNumberChild() {
        return numberChild;
    }

    public void setNumberChild(Integer numberChild) {
        this.numberChild = numberChild;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Boolean getPayOnline() {
        return isPayOnline;
    }

    public void setPayOnline(Boolean payOnline) {
        isPayOnline = payOnline;
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
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(code, order.code) && Objects.equals(note, order.note) && Objects.equals(numberAdult, order.numberAdult) && Objects.equals(numberChild, order.numberChild) && Objects.equals(totalAmount, order.totalAmount) && Objects.equals(isSuccess, order.isSuccess) && Objects.equals(isPayOnline, order.isPayOnline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, note, numberAdult, numberChild, totalAmount, isSuccess, isPayOnline);
    }
}
