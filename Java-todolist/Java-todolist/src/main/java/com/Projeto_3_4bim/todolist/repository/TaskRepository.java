package com.Projeto_3_4bim.todolist.repository;

import java.util.Optional;

import com.Projeto_3_4bim.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Projeto_3_4bim.todolist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
    Optional<Task> findByTaskname(String task_name);

    Optional<Task> findByTag(String tag);
}
