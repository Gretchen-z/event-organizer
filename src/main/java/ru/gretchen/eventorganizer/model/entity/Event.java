package ru.gretchen.eventorganizer.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "topic")
    private String topic;

    @Column(name = "description")
    private String description;

    @Column(name = "locality")
    private String locality;

    @Column(name = "date")
    private ZonedDateTime dateTime;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Workshop> workshops;

    @ManyToMany(mappedBy = "events")
    private List<User> users;
}
