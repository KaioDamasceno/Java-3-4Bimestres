# Flux --- Gerenciador de Tarefas

Projeto desenvolvido para os 3º e 4º bimestres da disciplina de
Laboratório de Programação 2 (LP2) no IFSP.\
O sistema consiste em um gerenciador de tarefas completo, com
autenticação, filtros, tags e interface moderna construída com Thymeleaf
e Bootstrap.

------------------------------------------------------------------------

## Sobre o Projeto

Este projeto implementa uma aplicação web do tipo To-Do List com foco
em:

-   Organização e gerenciamento de tarefas
-   Autenticação de usuários
-   Persistência de dados em SQLite
-   Boas práticas com Spring Boot, MVC, Security e JPA

------------------------------------------------------------------------

## Funcionalidades

-   Registro e login de usuários com senhas criptografadas (BCrypt)
-   Criar, listar, marcar como concluída e excluir tarefas
-   Sistema de tags para organização
-   Filtros de visualização:
    -   Tarefas do dia
    -   Próximos 7 dias
    -   Tarefas concluídas
    -   Filtrar por tag
-   Interface em dark mode, com design baseado em glassmorphism
-   Banco SQLite criado automaticamente na primeira execução
-   Templates HTML usando Thymeleaf

------------------------------------------------------------------------

## Tecnologias Utilizadas

### Back-end

-   Java 17\
-   Spring Boot 3.2\
-   Spring MVC\
-   Spring Security\
-   Spring Data JPA\
-   Hibernate\
-   SQLite

### Front-end

-   Thymeleaf\
-   HTML5\
-   Bootstrap 5\
-   CSS personalizado

### Ferramentas

-   Maven\
-   Git e GitHub\
-   IDE (VS Code, IntelliJ ou Eclipse)

------------------------------------------------------------------------

## Como Executar

### Pré-requisitos

-   Java JDK 17\
-   Maven\
-   Git

### Passo a passo

``` bash
git clone https://github.com/KaioDamasceno/Java-3-4Bimestres.git
cd Java-3-4Bimestres
```

1.  Abra o projeto na sua IDE.\
2.  Aguarde o Maven baixar as dependências.\
3.  Execute a classe principal:

```{=html}
<!-- -->
```
    src/main/java/com/Projeto_3_4bim/todolist/JavaTodolistApplication.java

4.  Acesse no navegador:

```{=html}
<!-- -->
```
    http://localhost:8080

------------------------------------------------------------------------

## Banco de Dados

-   Banco: SQLite\
-   Arquivo gerado automaticamente: `todolist.db`\
-   Ferramenta recomendada para visualização:\
    https://sqlitebrowser.org/

------------------------------------------------------------------------

## Estrutura do Projeto

    src/
     ├─ main/
     │   ├─ java/com/Projeto_3_4bim/todolist/
     │   │   ├─ controller/       # Controladores MVC
     │   │   ├─ service/          # Regras de negócio
     │   │   ├─ repository/       # JPA Repositories
     │   │   ├─ model/            # Entidades/Tabelas
     │   │   └─ security/         # Configuração do Spring Security
     │   └─ resources/
     │       ├─ templates/        # HTML (Thymeleaf)
     │       ├─ static/css        # Estilos
     │       └─ application.properties
     └─ test/

------------------------------------------------------------------------

## Fluxo da Aplicação

``` mermaid
flowchart TD
    A[Usuário acessa] --> B[Login ou Registro]
    B -->|Sucesso| C[Dashboard de Tarefas]
    C --> D[Adicionar Tarefa]
    C --> E[Filtrar por Data]
    C --> F[Filtrar por Tag]
    D --> G[Salvar no Banco]
    E --> G
    F --> G
    G --> C
```

------------------------------------------------------------------------

## Equipe

| Nome Completo                  | Prontuário |
| ------------------------------ | ---------- |
| Bruno Timóteo Silva            | SP3116042  |
| Fernando Pereira Filipe Duarte | SP311872X  |
| João Pedro de Almeida Martins  | SP3115933  |
| Kaio Damasceno de Oliveira     | SP3124517  |
| Matheus Barbosa Silva          | SP311659X  |

------------------------------------------------------------------------

## Licença

Projeto desenvolvido exclusivamente para fins acadêmicos na disciplina
de LP2 do IFSP.
