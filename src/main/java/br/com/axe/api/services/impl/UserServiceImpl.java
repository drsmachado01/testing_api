package br.com.axe.api.services.impl;

import br.com.axe.api.domain.User;
import br.com.axe.api.repositories.UserRepository;
import br.com.axe.api.services.UserService;
import br.com.axe.api.services.exceptions.DataIntegrityViolationException;
import br.com.axe.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public User create(User user) {
        findByEmail(user);
        return repo.save(user);
    }

    @Override
    public User update(Integer id, User updUser) {
        findById(id);
        findByEmail(updUser);
        updUser.setId(id);
        return repo.save(updUser);
    }

    @Override
    public void delete(Integer id) {
        repo.delete(findById(id));
    }

    private void findByEmail(User user) {
        Optional<User> entity = repo.findByEmail(user.getEmail());
        if(entity.isPresent() && !entity.get().getId().equals(user.getId())) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }
}
