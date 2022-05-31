package br.com.axe.api.services;

import br.com.axe.api.domain.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();

    User create(User user);

    User update(Integer id, User user);

    void delete(Integer id);
}
