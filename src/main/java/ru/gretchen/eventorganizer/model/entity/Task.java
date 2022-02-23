package ru.gretchen.eventorganizer.model.entity;

import lombok.*;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(name = "date_creating")
    private LocalDate dateCreating;

    @Column(name = "deadline")
    private LocalDate deadline;

    @JoinColumn(name = "executor_id")
    private UUID executorId;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
}
