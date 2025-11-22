package com.Projeto_3_4bim.todolist.controller;

import com.Projeto_3_4bim.todolist.model.User;
import com.Projeto_3_4bim.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

/**
 * Controlador responsável por lidar com as requisições de autenticação,
 * como login e registro de novos usuários, e pela página inicial.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Expressão regular para validar a força da senha
    // Requisitos: Mínimo 8 caracteres, 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial.
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * Exibe a página inicial de apresentação (Landing Page).
     * @return o nome da view da landing page (index.html)
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Exibe a página de login.
     * @return o nome da view de login (login.html)
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Exibe o formulário de registro.
     * @param model O modelo para adicionar atributos para a view.
     * @return o nome da view de registro (register.html)
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Garante que o objeto 'user' sempre exista no modelo,
        // especialmente ao redirecionar com erros.
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    /**
     * Processa o envio do formulário de registro.
     * @param user O objeto User preenchido com os dados do formulário.
     * @param model O modelo para adicionar atributos em caso de erro de validação.
     * @param redirectAttributes Atributos para adicionar mensagens após o redirecionamento.
     * @return Redireciona para a página de login em caso de sucesso, ou volta para a de registro em caso de erro.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        // 1. Verifica se o username já existe
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Este nome de usuário já está em uso. Por favor, escolha outro.");
            model.addAttribute("user", user); // Mantém os dados digitados
            return "register"; // Retorna para a página de registro com o erro
        }

        // 2. Validação da senha
        if (!pattern.matcher(user.getPassword()).matches()) {
            model.addAttribute("passwordError", "A senha não atende aos requisitos de segurança.");
            user.setPassword(""); // Limpa o campo de senha por segurança
            model.addAttribute("user", user); // Mantém o username digitado
            return "register"; // Retorna para a página de registro com o erro
        }

        // 3. Se tudo estiver correto, salva o usuário
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Usuário registrado com sucesso! Por favor, faça o login.");
        return "redirect:/login";
    }
}