package com.quest.etna.model;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique=true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Date creationDate;
    private Date updatedDate;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", creationDate=" + creationDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
