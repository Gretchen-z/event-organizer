package ru.gretchen.eventorganizer.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workshops")
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "speaker_id")
    private User speaker;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @Column(name = "topic")
    private String topic;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
