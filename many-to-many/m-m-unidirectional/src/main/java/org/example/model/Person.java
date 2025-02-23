package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * CREATE TABLE persons (
 *     id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
 * );
 *
 * --- The additional "link" table between the two joining entities:
 * CREATE TABLE persons_specialties (
 *     person_id INT NOT NULL,
 *     speciality_id INT NOT NULL,
 *     FOREIGN KEY (person_id) REFERENCES persons,
 *     FOREIGN KEY (speciality_id) REFERENCES specialties
 * );
 */
@Entity
@Table(name = "persons")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)  // Use PERSIST only if "speciality" has no unique constraints
    @JoinTable(  // Optional, mostly used to just specify the names of the table and its columns
            name = "persons_specialties",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private List<Speciality> specialties = new ArrayList<>();
}
