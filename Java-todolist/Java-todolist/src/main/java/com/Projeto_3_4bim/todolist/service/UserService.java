package com.Projeto_3_4bim.todolist.service;

import com.Projeto_3_4bim.todolist.model.User;
import com.Projeto_3_4bim.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Esta classe de serviço é responsável por carregar os detalhes de um usuário
 * do banco de dados e também por outras operações relacionadas ao usuário, como o registro.
 * É usada pelo Spring Security durante o processo de autenticação.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o encoder de senhas

    /**
     * Localiza o usuário com base no nome de usuário. No processo de autenticação,
     * o Spring Security chama este método para obter os detalhes do usuário
     * (como senha e permissões) para verificação.
     *
     * @param username o nome de usuário que está tentando se autenticar.
     * @return um objeto UserDetails contendo os dados do usuário.
     * @throws UsernameNotFoundException se o usuário não for encontrado no banco de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no repositório pelo username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Cria e retorna um objeto User do Spring Security
        // Este objeto contém as informações necessárias para a autenticação.
        // Por enquanto, não estamos usando roles/authorities, então passamos uma lista vazia.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    /**
     * Salva um novo usuário no banco de dados.
     * Antes de salvar, a senha do usuário é criptografada.
     * @param user o objeto User a ser salvo.
     */
    public void save(User user) {
        // Criptografa a senha antes de salvar no banco de dados
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Procura por um usuário pelo seu nome de usuário.
     * Este método delega a chamada para o repositório.
     * @param username O nome de usuário a ser procurado.
     * @return um Optional contendo o usuário se encontrado, ou um Optional vazio caso contrário.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
