package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.PersonDetails;

public class OneToOneBidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        persistingPerson();
        fetching();

        removingPersonDetails();
        fetching();

        emf.close();
    }

    static void persistingPerson() {

        System.out.println("\nPersisting Person:");

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

    static void removingPersonDetails() {

        System.out.println("\nRemoving PersonDetails from Person using 'orphanRemoval' (merging):");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            var person = em.find(Person.class, 1);
            person.removePersonDetails();
            person = em.merge(person);

            em.flush();

            var personDetailsAll = em.createQuery("FROM PersonDetails", PersonDetails.class).getResultList();
            if (!personDetailsAll.isEmpty()) {
                System.out.println("PersonDetails was not removed from the database!");
            }

            em.getTransaction().commit();
        }
    }

    static void fetching() {

        System.out.println("\nFetching:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            var person = em.find(Person.class, 1);
            System.out.println("Person: " + person);

            em.getTransaction().commit();
        }
    }
}