package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Person;
import org.example.model.Speciality;

import java.util.ArrayList;
import java.util.Random;

public class ManyToManyUnidirectional {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic-unit");

    public static void main(String[] args) {

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
                        person.getSpecialties().add(speciality);
                    }
                }

                em.persist(person);
            }

            em.getTransaction().commit();
        }

        emf.close();
    }
}