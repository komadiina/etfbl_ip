package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Manufacturer;
import org.unibl.etf.rest_api.repository.ManufacturerRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class ManufacturerService implements CRUDService<Manufacturer> {
    @Autowired
    private ManufacturerRepository repository;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return repository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (!repository.existsById(manufacturer.getManufacturerID()))
            return null;

        return repository.save(manufacturer);
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        if (!repository.existsById(manufacturer.getManufacturerID()))
            return false;

        repository.delete(manufacturer);
        return true;
    }

    @Override
    public Manufacturer retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
