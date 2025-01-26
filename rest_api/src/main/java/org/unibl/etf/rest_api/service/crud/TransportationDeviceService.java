package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.repository.TransportationDeviceRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class TransportationDeviceService implements CRUDService<TransportationDevice> {
    @Autowired
    private TransportationDeviceRepository repository;

    @Override
    public TransportationDevice create(TransportationDevice transportationDevice) {
        return repository.save(transportationDevice);
    }

    @Override
    public TransportationDevice update(TransportationDevice transportationDevice) {
        if (!repository.existsById(transportationDevice.getDeviceID()))
            return null;

        return repository.save(transportationDevice);
    }

    @Override
    public boolean delete(TransportationDevice transportationDevice) {
        if (!repository.existsById(transportationDevice.getDeviceID()))
            return false;

        repository.delete(transportationDevice);
        return true;
    }

    @Override
    public TransportationDevice retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
