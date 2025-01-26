package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Vehicle;
import org.unibl.etf.rest_api.repository.VehicleRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class VehicleService implements CRUDService<Vehicle> {
    @Autowired
    private VehicleRepository repository;

    @Override
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        if (!repository.existsById(vehicle.getVehicleID()) )
            return null;

        return repository.save(vehicle);
    }

    @Override
    public boolean delete(Vehicle vehicle) {
        if (repository.findById(vehicle.getVehicleID()).isEmpty())
            return false;

        repository.delete(vehicle);
        return true;
    }

    @Override
    public Vehicle retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
