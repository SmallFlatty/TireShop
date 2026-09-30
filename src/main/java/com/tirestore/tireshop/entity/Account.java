package com.tirestore.tireshop.entity;


import com.tirestore.tireshop.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "account")
public class Account {

    public Account(Role role, String fullName, String hashedPassword, String email) {
        this.role = role;
        this.fullName = fullName;
        this.hashedPassword = hashedPassword;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private int userId;


    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.OTHER)
    @Column(name = "role")
    private Role role;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();
}
