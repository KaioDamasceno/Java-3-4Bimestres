package com.Projeto_3_4bim.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * A classe Task representa a entidade de tarefa no banco de dados.
 * Cada instância desta classe corresponde a uma linha na tabela 'tarefas'.
 */
@Data // Anotação do Lombok para gerar getters, setters, equals, hashCode e toString.
@Entity // Anotação que marca esta classe como uma entidade JPA (mapeada para o banco).
@Table(name = "tasks") // Especifica o nome da tabela no banco de dados.
public class Task {

    @Id // Marca o campo 'id' como a chave primária da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática do ID.
    private Long id;

    @Column(unique = true, nullable = false) // Garante que o nome da tarefa seja único e não nulo.
    private String task_name;

    @Column()
    private String task_description;

    @Column()
    private String expiration_date; //Usando Java.time.LocalDateTime iremos comparar com a data de expiração no estilo ano-mês-diaThora:minuto:segundo

    @Column()
    private String status;

    @Column()
    private String tag;
}