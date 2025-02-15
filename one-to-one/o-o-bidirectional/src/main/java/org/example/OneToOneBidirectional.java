package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.PersonDetails;

public class OneToOneBidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        persistingAllFromPerson();
//        persistingAllFromPersonDetails();

        fetching();

        emf.close();
    }

    static void persistingAllFromPerson() {

        System.out.println("\nPersisting from Person:");

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
    }

    static void persistingAllFromPersonDetails() {

        System.out.println("\nPersisting from PersonDetails:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Creating "Person"
            var person = new Person();
            em.persist(person);  // If PersonDetails doesn't Cascade "persisting" to person, then we must persist them manually first.

            // Creating "PersonDetails"
            var personDetails = new PersonDetails();
            personDetails.setPerson(person);
            em.persist(personDetails);

            em.getTransaction().commit();
        }
    }

    static void fetching() {

        System.out.println("\nFetching:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            var personDetails = em.find(PersonDetails.class, 1);
            System.out.println("PersonDetails is referenced by such a person: " + personDetails.getPerson());

            em.getTransaction().commit();
        }
    }
}