package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.Speciality;

import java.util.ArrayList;

public class ManyToOneBidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        persistingAllFromPerson();
//        persistingAllFromSpecialty();

        fetching();

        emf.close();
    }

    static void persistingAllFromPerson() {

        System.out.println("\nPersisting from Person:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Creating "Speciality" first
            var speciality = new Speciality();
            em.persist(speciality);  // If Person doesn't Cascade "persisting" to specialties, then we must persist them manually first.

            // Creating "Persons"
            for (int i = 0; i < 5; i++) {
                var person = new Person();
                person.setSpeciality(speciality);
                em.persist(person);  // If Speciality doesn't Cascade "persisting" to persons, then we must persist them manually first.
            }

            em.getTransaction().commit();
        }
    }

    static void persistingAllFromSpecialty() {

        System.out.println("\nPersisting from Specialty:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Creating "Persons"
            var persons = new ArrayList<Person>();
            for (int i = 0; i < 5; i++) {
                var person = new Person();
                em.persist(person);  // If Speciality doesn't Cascade "persisting" to persons, then we must persist them manually first.
                persons.add(person);
            }

            // Creating "Speciality"
            var speciality = new Speciality();
            speciality.addPersons(persons);
            em.persist(speciality);

            em.getTransaction().commit();
        }
    }

    static void fetching() {

        System.out.println("\nFetching:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            var speciality = em.find(Speciality.class, 1);
            System.out.println("Speciality has such persons: " + speciality.getPersons());

            em.getTransaction().commit();
        }
    }
}