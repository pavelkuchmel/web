package main.org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport")
@ToString
public class Passport  {

    public Passport(int id){
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer id;

    @Column(name = "personal_id")
    private String personalID;

    @Column(name = "ind_id")
    private String indID;

    @Column(name = "exp_ts")
    private Timestamp expTS;

    @Column(name = "created_ts")
    private Timestamp createdTS;
}
