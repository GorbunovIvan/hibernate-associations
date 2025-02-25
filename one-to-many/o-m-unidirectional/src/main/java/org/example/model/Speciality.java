package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * CREATE TABLE specialities (
 *     id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
 * );
 *
 * --- The additional "link" table between the two joining entities:
 * CREATE TABLE specialities_persons (
 *     speciality_id INT NOT NULL,
 *     person_id INT NOT NULL UNIQUE,
 *     FOREIGN KEY (speciality_id) REFERENCES specialities,
 *     FOREIGN KEY (person_id) REFERENCES persons
 * );
 */
@Entity
@Table(name = "specialities")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString(exclude = { "persons" })
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Person> persons = new ArrayList<>();
}
