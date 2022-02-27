package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Date dob;
    private String password;
    @Column(length = 1000)
    private String note;
    private Boolean isActive;
    private Long creator;
    private Date created;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Comment> comments;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Order> orders;

    public Customer() {
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(String code, String name, String phone, String email, String address, Date dob, String password,
                    String note, Boolean isActive, Long creator, Date created) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.password = password;
        this.note = note;
        this.isActive = isActive;
        this.creator = creator;
        this.created = created;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(code, customer.code) && Objects.equals(name, customer.name) && Objects.equals(phone, customer.phone) && Objects.equals(email, customer.email) && Objects.equals(address, customer.address) && Objects.equals(dob, customer.dob) && Objects.equals(password, customer.password) && Objects.equals(note, customer.note) && Objects.equals(isActive, customer.isActive) && Objects.equals(creator, customer.creator) && Objects.equals(created, customer.created) && Objects.equals(comments, customer.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, phone, email, address, dob, password, note, isActive, creator, created, comments);
    }
}
