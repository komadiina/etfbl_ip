package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Rental;
import org.unibl.etf.rest_api.repository.RentalRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class RentalService implements CRUDService<Rental> {
    @Autowired
    private RentalRepository repository;

    @Override
    public Rental create(Rental rental) {
        return repository.save(rental);
    }

    @Override
    public Rental update(Rental rental) {
        if (!repository.existsById(rental.getRentalID()))
            return null;

        return repository.save(rental);
    }

    @Override
    public boolean delete(Rental rental) {
        if (!repository.existsById(rental.getRentalID()))
            return false;

        repository.delete(rental);
        return true;
    }

    @Override
    public Rental retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
