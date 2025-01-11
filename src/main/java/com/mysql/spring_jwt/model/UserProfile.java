package com.mysql.spring_jwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(
        name = "profile_id",
        nullable = false,
        referencedColumnName = "id"
    )
    @JsonBackReference
    private User user;

    @Column(
        name = "address",
        unique = true,
        columnDefinition = "TEXT"
    )
    private String address;

    @Column(
        name = "phone",
        unique = true
    )
    private String phone;

}
