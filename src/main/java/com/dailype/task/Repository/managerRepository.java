package com.dailype.task.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.dailype.task.Model.Manager;
import java.util.Optional;
import java.util.UUID;

public interface managerRepository extends JpaRepository<Manager, UUID> {





    Optional<com.dailype.task.Model.Manager> findByIdAndIsActive(UUID managerId, boolean b);
}
