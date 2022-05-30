package br.com.axe.api.services;

import br.com.axe.api.domain.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();
}
