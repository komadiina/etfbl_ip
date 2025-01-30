package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.DeviceWithLocationDto;
import org.unibl.etf.rest_api.model.db.Rental;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.repository.RentalRepository;
import org.unibl.etf.rest_api.repository.TransportationDeviceRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportationDeviceService implements CRUDService<TransportationDevice> {
    @Autowired
    private TransportationDeviceRepository repository;

    @Autowired
    private RentalRepository rentalRepository;

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

    public List<TransportationDevice> retrieveAll() {
        return repository.findAll();
    }

    public Page<TransportationDevice> retrieveAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<TransportationDevice> retrieveAllAvailable() {
        return repository.findAllAvailable();
    }

    public List<TransportationDevice> getAllUnrentedAndUnbroken() {
        return repository.findAll().stream().filter(td -> td.getStatus().equals("Available")).toList();
    }

    public List<DeviceWithLocationDto> retrieveAllAvailableWithPositionInfo() {
        List<TransportationDevice> availableDevices = repository.findAllAvailable();
        List<Rental> rentals = rentalRepository.findAll().stream()
                .filter(rental -> availableDevices.stream().anyMatch(ad -> ad.getDeviceID() == rental.getDeviceID()))
                .toList();

        List<DeviceWithLocationDto> deviceWithLocationDtos = new ArrayList<>();
        rentals.forEach(rental -> deviceWithLocationDtos.add(new DeviceWithLocationDto(retrieve(rental.getDeviceID()), rental.getDropoffX(), rental.getDropoffY())));

        return deviceWithLocationDtos;
    }
}
