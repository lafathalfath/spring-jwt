package com.mysql.spring_jwt.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
// @JsonIdentityInfo(
//     generator = ObjectIdGenerators.PropertyGenerator.class,
//     property = "id"
// )
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(
        name = "author",
        referencedColumnName = "id",
        nullable = false
    )
    @JsonIgnore
    private User author;

    @Column(
        name = "title",
        nullable = false,
        unique = true
    )
    private String title;

    @Column(
        name = "content",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String content;

    @Column(
        name = "image_url",
        nullable = true
    )
    private String imageUrl;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "p_article_category",
        joinColumns = @JoinColumn(
            name = "article_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
        )
    )
    private Set<Category> categories = new HashSet<>();

    public String getAuthorName() {
        return author.getUsername();
    }

}
