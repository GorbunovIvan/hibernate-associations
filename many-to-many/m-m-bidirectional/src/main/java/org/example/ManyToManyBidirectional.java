package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.Speciality;

import java.util.ArrayList;
import java.util.Random;

public class ManyToManyBidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

        persistingFromPerson();

        fetching();

        emf.close();
    }

    static void persistingFromPerson() {

        System.out.println("\nPersisting from Person:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Creating "Specialties" first
            var specialties = new ArrayList<Speciality>();
            for (int i = 0; i < 10; i++) {
                var speciality = new Speciality();
                specialties.add(speciality);
                em.persist(speciality);  // If Person doesn't Cascade "persisting" to specialties, then we must persist them manually first.
            }

            // Creating "Persons"
            for (int i = 0; i < 10; i++) {

                var person = new Person();

                // Adding at most 3 random specialties to a person
                for (int iS = 0; iS < 3; iS++) {
                    int randomSpecialityIndex = new Random().nextInt(specialties.size()-1);
                    var speciality = specialties.get(randomSpecialityIndex);
                    if (!person.getSpecialties().contains(speciality)) {
                        person.addSpeciality(speciality);
                    }
                }

                em.persist(person);
            }

            em.getTransaction().commit();
        }
    }

    static void fetching() {

        System.out.println("\nFetching:");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            var specialities = em.createQuery("FROM Speciality", Speciality.class).getResultList();
            for (var speciality : specialities) {
                System.out.printf("Speciality '%s' has such persons:\n %s\n", speciality, speciality.getPersons());
            }

            em.getTransaction().commit();
        }
    }
}