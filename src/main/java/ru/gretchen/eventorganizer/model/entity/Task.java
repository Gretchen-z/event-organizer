package ru.gretchen.eventorganizer.model.entity;

import lombok.*;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creating")
    private LocalDateTime dateCreating;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private TaskStatus status;
}
