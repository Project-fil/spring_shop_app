package com.github.ratel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Builder
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comments SET is_removed = true WHERE id=?")
@Where(clause = "is_removed=false")
public class Comment implements Serializable {

    @Transient
    private static final long serialVersionUID = 818395152219333330L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT", unique = true)
    private Long id;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "BIGINT")
    private Product product;

    @Column(name = "comment_text", nullable = false, columnDefinition = "TEXT")
    private String text;

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


    public Comment(User author, Product product, String text, Date createdDate) {
        this.author = author;
        this.product = product;
        this.text = text;
        this.createdDate = createdDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment that = (Comment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
