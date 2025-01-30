package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.ElectricBicycle;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.repository.ElectricBicycleRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectricBicycleService implements CRUDService<ElectricBicycle> {
    @Autowired
    private ElectricBicycleRepository repository;

    @Autowired
    private TransportationDeviceService transportationDeviceService;

    @Deprecated
    @Override
    public ElectricBicycle create(ElectricBicycle electricBicycle) {
        return repository.save(electricBicycle);
    }

    public ElectricBicycle add(TransportationDevice electricBicycle) {
        transportationDeviceService.create(electricBicycle);
        return repository.save((ElectricBicycle) electricBicycle);
    }

    @Override
    public ElectricBicycle update(ElectricBicycle electricBicycle) {
        if (!repository.existsById(electricBicycle.getDeviceID()))
            return null;

        return repository.save(electricBicycle);
    }

    @Override
    public boolean delete(ElectricBicycle electricBicycle) {
        if (!repository.existsById(electricBicycle.getDeviceID()))
            return false;

        repository.delete(electricBicycle);
        return true;
    }

    @Override
    public ElectricBicycle retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<ElectricBicycle> retrieveAll() {
        return repository.findAll();
    }
}
