package com.github.ratel.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE categories SET is_removed = true WHERE id=?")
@Where(clause = "is_removed=false")
public class Category implements Serializable {

    @Transient
    private static final long serialVersionUID = -7870245864375181013L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "TEXT")
    private String name;

    @ToString.Exclude
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "category",
            cascade = CascadeType.ALL
    )
    private Set<Subcategory> subcategories = new HashSet<>();

    @Column(name = "is_removed")
    private boolean removed;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date cratedAt;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;


    public Category(String name) {
        this.name = name;
    }


    public void addSubcategory(Subcategory subcategory) {
        if (subcategories.isEmpty())
            subcategories = new HashSet<>();
        this.subcategories.add(subcategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category that = (Category) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}