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
    private Long creator;
    private Date created;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    public Order() {
    }

    public Order(Long id, String code, String note, Integer numberAdult, Integer numberChild, BigDecimal totalAmount,
                 Boolean isSuccess, Boolean isPayOnline, Long creator, Date created) {
        this.id = id;
        this.code = code;
        this.note = note;
        this.numberAdult = numberAdult;
        this.numberChild = numberChild;
        this.totalAmount = totalAmount;
        this.isSuccess = isSuccess;
        this.isPayOnline = isPayOnline;
        this.creator = creator;
        this.created = created;
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

    public Long getCreator() {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        return Objects.equals(id, order.id) && Objects.equals(code, order.code) && Objects.equals(note, order.note) && Objects.equals(numberAdult, order.numberAdult) && Objects.equals(numberChild, order.numberChild) && Objects.equals(totalAmount, order.totalAmount) && Objects.equals(isSuccess, order.isSuccess) && Objects.equals(isPayOnline, order.isPayOnline) && Objects.equals(creator, order.creator) && Objects.equals(created, order.created) && Objects.equals(customer, order.customer) && Objects.equals(orderDetails, order.orderDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, note, numberAdult, numberChild, totalAmount, isSuccess, isPayOnline, creator, created, customer, orderDetails);
    }
}