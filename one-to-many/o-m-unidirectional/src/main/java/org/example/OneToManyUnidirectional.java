package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.Speciality;

import java.util.ArrayList;

public class OneToManyUnidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        persisting();
        fetching();

        emf.close();
    }

    static void persisting() {

        System.out.println("\nPersisting:");

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
            speciality.getPersons().addAll(persons);
            em.persist(speciality);

            em.getTransaction().commit();
        }
    }

    static void fetching() {

        System.out.println("\nFetching:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            var speciality = em.find(Speciality.class, 1);
            System.out.println("Specialty (id=1) has such persons:");
            speciality.getPersons().forEach(System.out::println);

            em.getTransaction().commit();
        }
    }
}