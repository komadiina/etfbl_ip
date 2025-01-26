package org.unibl.etf.rest_api.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Client;
import org.unibl.etf.rest_api.model.db.User;
import org.unibl.etf.rest_api.repository.ClientRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
public class ClientService implements CRUDService<Client> {
    @Autowired
    private ClientRepository repository;

    @Override
    public Client create(Client client) {
        return repository.save(client);
    }

    @Override
    public Client update(Client client) {
        if (!repository.existsById(client.getId()))
            return null;

        return repository.save(client);
    }

    @Override
    public boolean delete(Client client) {
        if (!repository.existsById(client.getId()))
            return false;

        repository.delete(client);
        return true;
    }

    @Override
    public Client retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
