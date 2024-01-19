package com.github.ratel.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_cart")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE cart SET is_removed = true WHERE id=?")
public class Cart implements Serializable {

    @Transient
    private static final long serialVersionUID = -2527059234543259013L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT", unique = true)
    private Long id;

    @ElementCollection
    @Column(name = "quantity")
    @MapKeyJoinColumn(name = "product_id")
    private Map<Product, Integer> products = new HashMap<>();

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_removed")
    private boolean removed;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date cratedAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return removed == cart.removed && id.equals(cart.id) && products.equals(cart.products) && user.equals(cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, user, removed);
    }

}
