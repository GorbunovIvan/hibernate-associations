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

            // Creating "Person" first
            var person = new Person();
            em.persist(person);  // If PersonDetails doesn't Cascade "persisting" to Person, then we must persist them manually first.

            // Creating "PersonDetails"
            var personDetails = new PersonDetails();
            personDetails.setPerson(person);
            em.persist(personDetails);

            em.getTransaction().commit();
        }

        emf.close();
    }
}