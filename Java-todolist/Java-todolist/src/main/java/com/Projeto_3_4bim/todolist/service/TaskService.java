package com.Projeto_3_4bim.todolist.service;

import com.Projeto_3_4bim.todolist.model.Task;
import com.Projeto_3_4bim.todolist.model.TaskStatus;
import com.Projeto_3_4bim.todolist.model.User;
import com.Projeto_3_4bim.todolist.repository.TaskRepository;
import com.Projeto_3_4bim.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço para gerir a lógica de negócios relacionada a tarefas.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Encontra todas as tarefas para um utilizador específico.
     * @param username O nome do utilizador.
     * @return Uma lista de tarefas do utilizador.
     */
    @Transactional(readOnly = true)
    public List<Task> getTasksForUser(String username) {
        User user = getUser(username);
        return taskRepository.findByUser(user);
    }

    /**
     * Cria uma nova tarefa para um utilizador.
     * @param task O objeto da tarefa a ser criado.
     * @param username O nome do utilizador que está a criar a tarefa.
     * @return A tarefa guardada.
     */
    @Transactional
    public Task createTask(Task task, String username) {
        User user = getUser(username);
        task.setUser(user);
        task.setStatus(TaskStatus.PENDING);
        return taskRepository.save(task);
    }

    /**
     * Alterna o estado de uma tarefa (PENDING <-> COMPLETED).
     * @param taskId O ID da tarefa a ser alterada.
     * @param username O nome do utilizador que está a tentar a alteração.
     */
    @Transactional
    public void toggleTaskStatus(Long taskId, String username) {
        Task task = getTaskByIdAndUser(taskId, username);
        TaskStatus newStatus = task.getStatus() == TaskStatus.PENDING ? TaskStatus.COMPLETED : TaskStatus.PENDING;
        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    /**
     * Apaga uma tarefa.
     * Garante que apenas o dono da tarefa a pode apagar.
     * @param taskId O ID da tarefa a ser apagada.
     * @param username O nome do utilizador que está a tentar apagar.
     */
    @Transactional
    public void deleteTask(Long taskId, String username) {
        Task task = getTaskByIdAndUser(taskId, username);
        taskRepository.delete(task);
    }

    // --- MÉTODOS DE FILTRO ---

    public List<Task> getTasksDueToday(String username) {
        User user = getUser(username);
        return taskRepository.findByUserAndDueDate(user, LocalDate.now());
    }

    public List<Task> getTasksDueInNext7Days(String username) {
        User user = getUser(username);
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return taskRepository.findByUserAndDueDateBetween(user, today, nextWeek);
    }

    public List<Task> getCompletedTasks(String username) {
        User user = getUser(username);
        return taskRepository.findByUserAndStatus(user, TaskStatus.COMPLETED);
    }

    // --- MÉTODOS DE TAGS ADICIONADOS ---

    public List<Task> getTasksByTag(String username, String tag) {
        User user = getUser(username);
        return taskRepository.findByUserAndTagsContaining(user, tag);
    }

    public Set<String> getAllUserTags(String username) {
        User user = getUser(username);
        List<Task> tasks = taskRepository.findByUser(user);
        return tasks.stream()
                .filter(task -> StringUtils.hasText(task.getTags())) // Ignora tags vazias ou nulas
                .flatMap(task -> Arrays.stream(task.getTags().split(","))) // Divide a string "tag1,tag2" em um stream de tags
                .map(String::trim) // Remove espaços em branco (ex: " tag1 " -> "tag1")
                .collect(Collectors.toSet()); // Coleta em um Set para garantir que as tags sejam únicas
    }


    // --- MÉTODOS AUXILIARES PRIVADOS ---

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado: " + username));
    }

    private Task getTaskByIdAndUser(Long taskId, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com ID: " + taskId));

        if (!task.getUser().getUsername().equals(username)) {
            throw new SecurityException("Acesso negado: Você não tem permissão para aceder a esta tarefa.");
        }
        return task;
    }
}

