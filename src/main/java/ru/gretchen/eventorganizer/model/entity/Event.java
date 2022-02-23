package ru.gretchen.eventorganizer.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "topic")
    private String topic;

    @Column(name = "description")
    private String description;

    @Column(name = "locality")
    private String locality;

    @Column(name = "date")
    private LocalDate dateTime;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<Workshop> workshops = new ArrayList<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "events",
            fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public void addWorkshop(Workshop workshop) {
        this.workshops.add(workshop);
        workshop.setEvent(this);
    }

    public void removeWorkshop(Workshop workshop) {
        this.workshops.remove(workshop);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }
}
