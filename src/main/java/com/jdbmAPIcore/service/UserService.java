package com.jdbmAPIcore.service;

import com.jdbmAPIcore.entity.User;
import com.jdbmAPIcore.exception.UserNotFoundException;
import com.jdbmAPIcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UserRepository userRepository;

    //NoPointerException не будет, так как спринг сам создаст класс, его объект и присвоит объект в нашу ссылку. Dependencies Ingection - паттерн
    @Autowired //Явно указываем, что в этом поле хотим увидеть какой-то объект
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(String username, String password, Long phone) {

        if (userRepository.findByUsername(username) != null) {
            return null;
        }

        User user = new User(username, new BCryptPasswordEncoder().encode(password), phone, true, true, true, true);

        String sql = "create schema " + username + "_schema;";

        jdbcTemplate.execute(sql);

        return userRepository.save(user).getId();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Can't find user with id: " + id));
    }

    public User getUserByUsername(String username) {
        if (userRepository.findByUsername(username) == null) {
            return null;
        }
        return userRepository.findByUsername(username);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User deleteById(Long id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
        return user;
    }

}
