package edu.miu.waa.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Role> roles;

    @Override
    public String toString() {
        return username;
    }
}
