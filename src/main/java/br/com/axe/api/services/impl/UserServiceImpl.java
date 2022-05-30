package br.com.axe.api.services.impl;

import br.com.axe.api.domain.User;
import br.com.axe.api.repositories.UserRepository;
import br.com.axe.api.services.UserService;
import br.com.axe.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repo;
    @Override
    public User findById(Integer id) {
        Optional<User> oUser = repo.findById(id);
        return oUser.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }
}
