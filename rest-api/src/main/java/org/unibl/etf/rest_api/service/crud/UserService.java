package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.User;
import org.unibl.etf.rest_api.repository.UserRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CRUDService<User> {
    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        if (!repository.existsById(user.getId()))
            return null;

        return repository.save(user);
    }

    @Override
    public boolean delete(User user) {
        if (!repository.existsById(user.getId()))
            return false;

        repository.delete(user);
        return true;
    }

    @Override
    public User retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> retrieveAll() {
        return repository.findAll();
    }

    public User retrieve(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public Page<User> retrieveAllPaginated(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
}
