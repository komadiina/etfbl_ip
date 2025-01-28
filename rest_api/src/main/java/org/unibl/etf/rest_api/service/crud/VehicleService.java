package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.model.db.Vehicle;
import org.unibl.etf.rest_api.model.dto.VehicleDto;
import org.unibl.etf.rest_api.repository.TransportationDeviceRepository;
import org.unibl.etf.rest_api.repository.VehicleRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService implements CRUDService<Vehicle> {
    @Autowired
    private VehicleRepository repository;

    @Autowired
    private TransportationDeviceRepository transportationDeviceRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Deprecated
    @Override
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public Vehicle add(TransportationDevice td) {
        transportationDeviceRepository.save(td);
        return vehicleRepository.save((Vehicle)td);
    }

    public Vehicle add(VehicleDto dto) {
        TransportationDevice td = new Vehicle();
        td.setDescription(dto.getDescription());
        td.setDeviceType(dto.getDeviceType());
        td.setManufacturerID(dto.getManufacturerID());
        td.setModel(dto.getModel());
        td.setPurchasePrice(dto.getPurchasePrice());
        td.setStatus(dto.getStatus());
        td.setUUID(dto.getUUID());
        td.setAcquisitionDate(dto.getAcquisitionDate());
        td = transportationDeviceRepository.save(td);

        return vehicleRepository.save((Vehicle)td);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        if (!repository.existsById(vehicle.getDeviceID()) )
            return null;

        return repository.save(vehicle);
    }

    @Override
    public boolean delete(Vehicle vehicle) {
        if (repository.findById(vehicle.getDeviceID()).isEmpty())
            return false;

        repository.delete(vehicle);
        return true;
    }

    @Override
    public Vehicle retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Vehicle> retrieveAll() {
        return repository.findAll();
    }

    public Page<Vehicle> retrieveAllPaginated(PageRequest page) {
        return repository.findAll(page);
    }
}
