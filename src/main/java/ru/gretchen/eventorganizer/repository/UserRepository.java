package ru.gretchen.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gretchen.eventorganizer.model.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByEvents_Id(Long eventId);

    List<User> findAllBySurname(String surname);
}
