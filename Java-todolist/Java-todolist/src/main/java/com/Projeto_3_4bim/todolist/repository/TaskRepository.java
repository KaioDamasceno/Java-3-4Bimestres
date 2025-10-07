package com.Projeto_3_4bim.todolist.repository;

import com.Projeto_3_4bim.todolist.model.Task;
import com.Projeto_3_4bim.todolist.model.TaskStatus;
import com.Projeto_3_4bim.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para a entidade Task.
 * Fornece métodos para realizar operações de CRUD (Create, Read, Update, Delete)
 * na tabela de tarefas.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Encontra todas as tarefas associadas a um usuário específico.
     * @param user O usuário cujas tarefas devem ser encontradas.
     * @return Uma lista de tarefas pertencentes ao usuário.
     */
    List<Task> findByUser(User user);

    /**
     * Encontra todas as tarefas de um usuário que vencem hoje.
     */
    List<Task> findByUserAndDueDate(User user, LocalDate date);

    /**
     * Encontra todas as tarefas de um usuário que vencem dentro de um intervalo de datas.
     */
    List<Task> findByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate);

    /**
     * Encontra todas as tarefas de um usuário com um estado específico.
     */
    List<Task> findByUserAndStatus(User user, TaskStatus status);

    /**
     * Encontra todas as tarefas de um usuário que contenham uma tag específica.
     * A consulta irá procurar pela string da tag dentro da coluna 'tags'.
     */
    List<Task> findByUserAndTagsContaining(User user, String tag);

}

