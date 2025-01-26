package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Breakdown;
import org.unibl.etf.rest_api.repository.BreakdownRepository;
import org.unibl.etf.rest_api.service.CRUDService;

@Service
@RequiredArgsConstructor
public class BreakdownService implements CRUDService<Breakdown> {
    @Autowired
    private BreakdownRepository repository;

    @Override
    public Breakdown create(Breakdown breakdown) {
        return repository.save(breakdown);
    }

    @Override
    public Breakdown update(Breakdown breakdown) {
        if (!repository.existsById(breakdown.getBreakdownID()))
            return null;

        return repository.save(breakdown);
    }

    @Override
    public boolean delete(Breakdown breakdown) {
        if (!repository.existsById(breakdown.getBreakdownID()))
            return false;

        repository.delete(breakdown);
        return true;
    }

    @Override
    public Breakdown retrieve(int id) {
        return repository.findById(id).orElse(null);
    }
}
