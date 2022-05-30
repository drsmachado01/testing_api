package br.com.axe.api.services;

import br.com.axe.api.domain.User;

public interface UserService {
    User findById(Integer id);
}
