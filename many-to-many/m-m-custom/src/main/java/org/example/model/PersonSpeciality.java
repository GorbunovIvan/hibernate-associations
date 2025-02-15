package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;


    // Utility methods that synchronize both ends whenever a child element is added or removed.
    public void addAssociationToBothSides() {

        // Synchronizing Person
        if (person == null) {
            throw new IllegalArgumentException("Unable to link a person and a speciality - person is null: " + this);
        }
        if (!person.getPersonsSpecialties().contains(this)) {
            person.getPersonsSpecialties().add(this);
        }

        // Synchronizing Speciality
        if (speciality == null) {
            throw new IllegalArgumentException("Unable to link a person and a speciality - speciality is null: " + this);
        }
        if (!speciality.getPersonsSpecialties().contains(this)) {
            speciality.getPersonsSpecialties().add(this);
        }
    }

    public void removeAssociationFromBothSides() {

        // Synchronizing Person
        if (person == null) {
            throw new IllegalArgumentException("Unable to unlink a person and a speciality - person is null: " + this);
        }
        person.getPersonsSpecialties().remove(this);

        // Synchronizing Speciality
        if (speciality == null) {
            throw new IllegalArgumentException("Unable to unlink a person and a speciality - speciality is null: " + this);
        }
        speciality.getPersonsSpecialties().remove(this);
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
