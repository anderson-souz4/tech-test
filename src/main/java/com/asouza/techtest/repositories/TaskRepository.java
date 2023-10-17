package com.asouza.techtest.repositories;

import com.asouza.techtest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
