package com.mysql.spring_jwt.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter // child of many to many relation must be @Getter/@Setter, @Data can't be working 
@Setter // child of many to many relation must be @Getter/@Setter, @Data can't be working 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
// @JsonIdentityInfo(
//     generator = ObjectIdGenerators.PropertyGenerator.class,
//     property = "id"
// )
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(
        name = "name",
        nullable = false,
        unique = true
    )
    private String name;

    @JsonIgnore
    @ManyToMany(
        mappedBy = "categories",
        fetch = FetchType.LAZY
    )
    private Set<Article> articles = new HashSet<>();

}
