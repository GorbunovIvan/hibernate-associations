package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * <pre>
 * CREATE TABLE persons_specialties (
 *     person_id INT NOT NULL,
 *     speciality_id INT NOT NULL,
 *     FOREIGN KEY (person_id) REFERENCES persons,
 *     FOREIGN KEY (speciality_id) REFERENCES specialties,
 *     PRIMARY KEY (person_id, speciality_id)
 * );
 */
@Entity
@Table(name = "persons_specialties")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class PersonSpeciality {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")  // Optional, mostly used to just specify the column name (as in @Column)
    private Person person;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "speciality_id")  // Optional, mostly used to just specify the column name (as in @Column)
    private Speciality speciality;


    // Utility methods that synchronize both ends whenever a child element is added or removed.
    public void addAssociationToBothSides() {

        if (person == null) {
            throw new IllegalArgumentException("Unable to link a person and a speciality - person is null: " + this);
        }
        if (speciality == null) {
            throw new IllegalArgumentException("Unable to link a person and a speciality - speciality is null: " + this);
        }

        if (Hibernate.isInitialized(person.getPersonsSpecialties())) {
            if (!person.getPersonsSpecialties().contains(this)) {
                person.getPersonsSpecialties().add(this);
            }
        }
        if (Hibernate.isInitialized(speciality.getPersonsSpecialties())) {
            if (!speciality.getPersonsSpecialties().contains(this)) {
                speciality.getPersonsSpecialties().add(this);
            }
        }
    }

    public void removeAssociationFromBothSides() {

        if (person == null) {
            throw new IllegalArgumentException("Unable to unlink a person and a speciality - person is null: " + this);
        }
        if (speciality == null) {
            throw new IllegalArgumentException("Unable to unlink a person and a speciality - speciality is null: " + this);
        }

        if (Hibernate.isInitialized(person.getPersonsSpecialties())) {
            person.getPersonsSpecialties()
                    .removeIf(ps -> Objects.equals(ps.getPerson(), person)
                                 && Objects.equals(ps.getSpeciality(), speciality));
        }
        if (Hibernate.isInitialized(speciality.getPersonsSpecialties())) {
            speciality.getPersonsSpecialties()
                    .removeIf(ps -> Objects.equals(ps.getPerson(), person)
                                 && Objects.equals(ps.getSpeciality(), speciality));
        }
    }

    // Avoiding circular dependency in toString() method
    @Override
    public String toString() {
        return "PersonSpeciality{" +
                "person=" + (person != null ? person.getId() : null) +
                ", speciality=" + (speciality != null ? speciality.getId() : null) +
                '}';
    }
}
