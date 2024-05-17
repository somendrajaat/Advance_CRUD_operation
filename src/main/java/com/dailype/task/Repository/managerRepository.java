package com.dailype.task.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.dailype.task.Model.Manager;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface managerRepository extends JpaRepository<Manager, UUID> {
    @Query("SELECT m FROM Manager m LEFT JOIN FETCH m.people")
    List<Manager> findAllWithPeople();
    Optional<com.dailype.task.Model.Manager> findByIdAndIsActive(UUID managerId, boolean b);
}
