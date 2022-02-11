package ru.gretchen.eventorganizer.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
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
    private ZonedDateTime dateTime;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private List<Workshop> workshops;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private Set<User> users;
}
