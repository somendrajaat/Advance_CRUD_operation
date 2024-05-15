package com.dailype.task.Repository;
import com.dailype.task.Model.people;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<people, UUID> {
    List<people> findByMobNum(String mobNum);
    List<people> findByManagerId(UUID managerId);
}