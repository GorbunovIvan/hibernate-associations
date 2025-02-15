package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.Speciality;

public class ManyToOneUnidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Creating "Speciality" first
            var speciality = new Speciality();
            em.persist(speciality);  // If Person doesn't Cascade "persisting" to specialties, then we must persist them manually first.

            // Creating "Persons"
            for (int i = 0; i < 5; i++) {
                var person = new Person();
                person.setSpeciality(speciality);
                em.persist(person);
            }

            em.getTransaction().commit();
        }

        emf.close();
    }
}