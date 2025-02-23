package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * CREATE TABLE specialties (
 *     id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
 * )
 */
@Entity
@Table(name = "specialties")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString(exclude = { "persons" })
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL)  // This single annotation makes the association "Bidirectional"
    @Setter(AccessLevel.PRIVATE)
    private List<Person> persons = new ArrayList<>();


    // Utility methods that synchronize both ends whenever a child element is added or removed.
    public void addPersons(Collection<Person> persons) {
        persons.forEach(this::addPerson);
    }
    public void addPerson(Person person) {
        person.setSpeciality(this);
        if (!persons.contains(person)) {
            persons.add(person);
        }
    }

    public void removePersons(Collection<Person> persons) {
        persons.forEach(this::removePerson);
    }
    public void removePerson(Person person) {
        persons.remove(person);
        person.setSpeciality(null);
    }
}
