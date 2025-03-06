package com.github.ratel.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE products SET is_removed = true WHERE id=?")
public class Product implements Serializable {

    @Transient
    private static final long serialVersionUID = -4343383509861032886L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT", unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "vendor_code", nullable = false)
    private String vendorCode;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, orphanRemoval = true)
    @JoinTable(
            name = "product_files",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<FileEntity> files = new HashSet<>();

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id")
    private Subcategory subcategory;

    @Column(name = "brand")
    private String brand;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Comment> comments;

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

    public Product(String vendorCode, String name, BigDecimal price, int quantity) {
        this.vendorCode = vendorCode;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String vendorCode, String name, BigDecimal price, Subcategory subcategory) {
        this.vendorCode = vendorCode;
        this.subcategory = subcategory;
        this.name = name;
        this.price = price;
    }

    public void addFile(FileEntity fileEntity) {
        if (this.files.isEmpty())
            this.files = new HashSet<>();
        this.files.add(fileEntity);
    }

    public void addComment(Comment comment) {
        if (this.comments.isEmpty())
            this.comments = new ArrayList<>();
        this.comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product that = (Product) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
