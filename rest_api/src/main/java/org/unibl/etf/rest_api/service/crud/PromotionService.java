package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Promotion;
import org.unibl.etf.rest_api.repository.PromotionRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class PromotionService implements CRUDService<Promotion> {
    @Autowired
    private PromotionRepository repository;

    @Override
    public Promotion create(Promotion promotion) {
        return repository.save(promotion);
    }

    @Override
    public Promotion update(Promotion promotion) {
        if (!repository.existsById(promotion.getPromotionID()))
            return null;

        return repository.save(promotion);
    }

    @Override
    public boolean delete(Promotion promotion) {
        if (!repository.existsById(promotion.getPromotionID()))
            return false;

        repository.delete(promotion);
        return true;
    }

    @Override
    public Promotion retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
