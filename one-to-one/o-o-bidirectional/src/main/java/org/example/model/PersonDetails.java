package org.example.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <pre>
 * CREATE TABLE person_details (
 *     id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
 *     person_id INTEGER NOT NULL UNIQUE,
 *     FOREIGN KEY (person_id) REFERENCES person
 * )
 */
@Entity
@Table(name = "person_details")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class PersonDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Means "FOREIGN KEY (person_details_id) REFERENCES person_details(id)"
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")  // Optional, mostly used to just specify the column name (as in @Column)
    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    private Person person;

    @ToString.Include(name = "person-id")
    public Integer getPersonId() {
        return person != null ? person.getId() : null;
    }
}
