package com.Projeto_3_4bim.todolist.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Representa uma tarefa no banco de dados.
 * Cada tarefa pertence a um único usuário.
 */
@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private LocalDate dueDate; // Data de vencimento

    @Enumerated(EnumType.STRING) // Salva o nome do status (ex: "PENDING")
    private TaskStatus status;

    private String tags; // Por simplicidade, vamos usar uma String com tags separadas por vírgula

    @ManyToOne(fetch = FetchType.LAZY) // Muitas tarefas para um usuário. LAZY é uma otimização.
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
