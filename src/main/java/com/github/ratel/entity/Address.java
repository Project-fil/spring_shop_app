package com.github.ratel.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_address")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user_address SET is_removed = true WHERE id=?")
@Where(clause = "is_removed=false")
public class Address implements Serializable {

    @Transient
    private static final long serialVersionUID = -3314738285065551787L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "phone", columnDefinition = "TEXT")
    private String phone;

    @Column(name = "country", columnDefinition = "TEXT")
    private String country;

    @Column(name = "city", columnDefinition = "TEXT")
    private String city;

    @Column(name = "street", columnDefinition = "TEXT")
    private String street;

    @Column(name = "houseNumber", columnDefinition = "TEXT")
    private String houseNumber;

    @Column(name = "apartmentNumber", columnDefinition = "TEXT")
    private String apartmentNumber;

    @ToString.Exclude
    @OneToMany(mappedBy = "address")
    private List<User> userAddress = new ArrayList<>();

    @Column(name = "is_removed")
    private boolean removed;

    @CreationTimestamp
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @UpdateTimestamp
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public void addUser(User user) {
        if (Objects.isNull(this.userAddress))
            this.userAddress = new ArrayList<>();
        this.userAddress.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address that = (Address) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
