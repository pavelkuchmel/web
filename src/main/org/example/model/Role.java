package main.org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,
            unique = true,
            columnDefinition="VARCHAR(255) COLLATE utf8_general_ci"
    )
    private String name;

    @Column
    private String details;

    @Column(name = "created_ts")
    private Timestamp created;

    @Column(name = "updated_ts")
    private Timestamp updated;

}
