package com.Projeto_3_4bim.todolist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig é a classe principal para configurar o Spring Security.
 * Aqui definimos as regras de acesso às URLs, o formulário de login e o
 * encoder de senhas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define o bean do PasswordEncoder, que será usado em toda a aplicação
     * para criptografar e verificar senhas. Usamos o BCrypt, que é o padrão
     * recomendado.
     * @return uma instância de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança do HTTP.
     * É aqui que as regras de autorização para as rotas da aplicação são definidas.
     * @param http o objeto HttpSecurity a ser configurado.
     * @return a cadeia de filtros de segurança construída.
     * @throws Exception se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Permite o acesso público à página inicial (/), registro, login e recursos estáticos
                        .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // Qualquer outra requisição exige que o usuário esteja autenticado.
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Define a URL da página de login personalizada.
                        .loginPage("/login")
                        // A URL para onde o formulário de login envia os dados (POST).
                        .loginProcessingUrl("/login")
                        // URL para redirecionar após um login bem-sucedido.
                        .defaultSuccessUrl("/tasks", true)
                        // Permite que todos acessem a página de login.
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        // Permite que todos acessem a funcionalidade de logout.
                        .permitAll()
                );

        return http.build();
    }
}