package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.PersonDetails;

public class OneToOneUnidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Creating "PersonDetails" first
            var personDetails = new PersonDetails();
            em.persist(personDetails);  // If Person doesn't Cascade "persisting" to personDetails, then we must persist them manually first.

            // Creating "Person"
            var person = new Person();
            person.setPersonDetails(personDetails);
            em.persist(person);

            em.getTransaction().commit();
        }

        emf.close();
    }
}