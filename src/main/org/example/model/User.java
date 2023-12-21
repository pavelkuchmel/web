package main.org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column(unique = true, columnDefinition="VARCHAR(255) COLLATE utf8_general_ci")
    private String email;

    @Column
    private String pwd;

    @Column
    private String details;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "role",
            referencedColumnName = "name"
    )
    private Role role;

    @Column(name = "created_ts")
    private Timestamp created;

    @Column(name = "updated_ts")
    private Timestamp updated;

}
