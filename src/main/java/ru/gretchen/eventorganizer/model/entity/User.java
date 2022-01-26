package ru.gretchen.eventorganizer.model.entity;

import lombok.*;
import ru.gretchen.eventorganizer.model.enumeration.ApplicationStatus;
import ru.gretchen.eventorganizer.model.enumeration.Gender;
import ru.gretchen.eventorganizer.model.enumeration.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "locality")
    private String locality;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    // todo: сделать отдельную таблицу со статусом заявки, связывающую user и event
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "role")
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "users_events",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    private List<Event> events;
}
