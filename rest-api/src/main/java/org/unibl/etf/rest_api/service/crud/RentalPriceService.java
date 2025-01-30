package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.RentalPrice;
import org.unibl.etf.rest_api.repository.RentalPriceRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalPriceService implements CRUDService<RentalPrice> {
    @Autowired
    private RentalPriceRepository repository;
    @Autowired
    private TransportationDeviceService transportationDeviceService;

    @Override
    public RentalPrice create(RentalPrice rentalPrice) {
        // find if an actual price already exists for this device
        RentalPrice activePrice = retrieveDeviceActual(rentalPrice.getDeviceID());
        if (activePrice != null) {
            // invalidate the given price
            activePrice.setActive(false);
            repository.save(activePrice);
        }

        return repository.save(rentalPrice);
    }

    @Override
    public RentalPrice update(RentalPrice rentalPrice) {
        if (!repository.existsById(rentalPrice.getPriceID()))
            return null;

        return repository.save(rentalPrice);
    }

    @Override
    public boolean delete(RentalPrice rentalPrice) {
        if (!repository.existsById(rentalPrice.getPriceID()))
            return false;

        repository.delete(rentalPrice);
        return true;
    }

    @Override
    public RentalPrice retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<RentalPrice> retrieveAll() {
        return repository.findAll();
    }

    public Page<RentalPrice> retrieveAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public RentalPrice retrieveDeviceActual(int deviceID) {
        // get the last defined rentalprice for deviceID
        List<RentalPrice> forDevice = repository.findByDeviceID(deviceID);

        if (forDevice.isEmpty())
            return null;

        if (forDevice.size() == 1)
            return forDevice.get(0);
        else return forDevice.get(forDevice.size() - 1);
    }

    public Page<RentalPrice> retrieveAllByDevicePaginated(int deviceID, Pageable pageable) {
        return repository.findByDeviceID(deviceID, pageable);
    }
}
