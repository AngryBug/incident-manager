package com.incident.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;
    
    private String lastName;

    private String phone;
    
    private String fax;
    
    @Column(nullable = false)
    private Long mobileNumber;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String pincode;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String state;
    
    @Column(nullable = false)
    private String country;
    
    @Column(nullable = false)
    private String countryCode;
    
    @Column(nullable = false)
    private String password;
}
