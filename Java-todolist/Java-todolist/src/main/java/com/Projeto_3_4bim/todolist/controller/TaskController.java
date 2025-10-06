package com.Projeto_3_4bim.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Controlador responsável por lidar com as requisições para a página principal
 * da aplicação, que é protegida e só pode ser acessada por usuários autenticados.
 */
@Controller
public class TaskController {

    /**
     * Exibe a página principal de tarefas após o login.
     * @param principal O objeto Principal injetado pelo Spring Security, contém os dados do usuário logado.
     * @param model O modelo para adicionar atributos para a view.
     * @return o nome da view de tarefas (tasks.html)
     */
    @GetMapping("/tasks")
    public String tasks(Principal principal, Model model) {
        // O Spring Security garante que 'principal' não será nulo se o usuário estiver autenticado.
        // Adicionamos o nome do usuário ao modelo para que o HTML possa acessá-lo.
        model.addAttribute("username", principal.getName());

        // Para depuração, vamos imprimir o nome no console para ter 100% de certeza.
        System.out.println("Usuário acessando /tasks: " + principal.getName());

        return "tasks";
    }
}

