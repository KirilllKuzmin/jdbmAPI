package com.jdbmAPIcore.service;

import com.jdbmAPIcore.entity.User;
import com.jdbmAPIcore.exception.UserNotFoundException;
import com.jdbmAPIcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    //NoPointerException не будет, так как спринг сам создаст класс, его объект и присвоит объект в нашу ссылку. Dependencies Ingection - паттерн
    @Autowired //Явно указываем, что в этом поле хотим увидеть какой-то объект
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createAccount(String name, String login, String password, Long phone) {
        User user = new User(name, login, password, phone);
        return userRepository.save(user).getId();
    }

    public User getAccountById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Can't find user with id: " + id));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User deleteById(Long id) {
        User user = getAccountById(id);
        userRepository.deleteById(id);
        return user;
    }

}
