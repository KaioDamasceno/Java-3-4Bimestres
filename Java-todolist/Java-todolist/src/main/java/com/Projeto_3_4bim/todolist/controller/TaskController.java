package com.Projeto_3_4bim.todolist.controller;

import com.Projeto_3_4bim.todolist.model.Task;
import com.Projeto_3_4bim.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Controlador responsável por lidar com as requisições para a página principal
 * da aplicação, que é protegida e só pode ser acessada por usuários autenticados.
 */
@Controller
@RequestMapping("/tasks") // Adicionamos um mapeamento base para todas as rotas de tarefas
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Exibe a página principal com todas as tarefas do usuário.
     */
    @GetMapping
    public String showAllTasks(Principal principal, Model model) {
        List<Task> tasks = taskService.getTasksForUser(principal.getName());
        prepareModelForView(principal, model, tasks, "Todas as Tarefas");
        return "tasks";
    }

    /**
     * Processa o envio do formulário para adicionar uma nova tarefa.
     */
    @PostMapping("/add")
    public String addTask(@ModelAttribute("newTask") Task newTask, Principal principal) {
        taskService.createTask(newTask, principal.getName());
        return "redirect:/tasks";
    }

    /**
     * Alterna o estado de uma tarefa (PENDING <-> COMPLETED).
     */
    @PostMapping("/{id}/toggle")
    public String toggleTaskStatus(@PathVariable Long id, Principal principal) {
        taskService.toggleTaskStatus(id, principal.getName());
        return "redirect:/tasks";
    }

    /**
     * Apaga uma tarefa.
     */
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id, Principal principal) {
        taskService.deleteTask(id, principal.getName());
        return "redirect:/tasks";
    }

    // --- MÉTODOS DE FILTRO ADICIONADOS ---

    @GetMapping("/today")
    public String showTasksDueToday(Principal principal, Model model) {
        List<Task> tasks = taskService.getTasksDueToday(principal.getName());
        prepareModelForView(principal, model, tasks, "Tarefas para Hoje");
        return "tasks";
    }

    @GetMapping("/week")
    public String showTasksDueInNext7Days(Principal principal, Model model) {
        List<Task> tasks = taskService.getTasksDueInNext7Days(principal.getName());
        prepareModelForView(principal, model, tasks, "Próximos 7 Dias");
        return "tasks";
    }

    @GetMapping("/completed")
    public String showCompletedTasks(Principal principal, Model model) {
        List<Task> tasks = taskService.getCompletedTasks(principal.getName());
        prepareModelForView(principal, model, tasks, "Tarefas Concluídas");
        return "tasks";
    }

    // --- MÉTODO AUXILIAR PRIVADO ---

    private void prepareModelForView(Principal principal, Model model, List<Task> tasks, String pageTitle) {
        model.addAttribute("tasks", tasks);
        model.addAttribute("username", principal.getName());
        model.addAttribute("newTask", new Task());
        model.addAttribute("pageTitle", pageTitle); // Título dinâmico para a página
    }
}

