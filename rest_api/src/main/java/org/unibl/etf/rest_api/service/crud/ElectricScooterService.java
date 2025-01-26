package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.ElectricScooter;
import org.unibl.etf.rest_api.repository.ElectricScooterRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class ElectricScooterService implements CRUDService<ElectricScooter> {
    @Autowired
    private ElectricScooterRepository repository;

    @Override
    public ElectricScooter create(ElectricScooter electricScooter) {
        return repository.save(electricScooter);
    }

    @Override
    public ElectricScooter update(ElectricScooter electricScooter) {
        if (!repository.existsById(electricScooter.getScooterID()))
            return null;

        return repository.save(electricScooter);
    }

    @Override
    public boolean delete(ElectricScooter electricScooter) {
        if (!repository.existsById(electricScooter.getScooterID()))
            return false;

        repository.delete(electricScooter);
        return true;
    }

    @Override
    public ElectricScooter retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
