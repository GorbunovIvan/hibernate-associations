package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

/**
 * <pre>
 * CREATE TABLE specialties (
 *     id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
 * )
 */
@Entity
@Table(name = "specialties")
@BatchSize(size = 20)  // To mitigate "N+1", when "specialties" are fetched for "persons"
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
