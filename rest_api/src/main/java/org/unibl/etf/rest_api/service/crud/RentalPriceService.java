package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.RentalPrice;
import org.unibl.etf.rest_api.repository.RentalPriceRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class RentalPriceService implements CRUDService<RentalPrice> {
    @Autowired
    private RentalPriceRepository repository;

    @Override
    public RentalPrice create(RentalPrice rentalPrice) {
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
}
