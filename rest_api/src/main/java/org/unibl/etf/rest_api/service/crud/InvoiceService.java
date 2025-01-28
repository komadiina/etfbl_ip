package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Invoice;
import org.unibl.etf.rest_api.repository.InvoiceRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService implements CRUDService<Invoice> {
    @Autowired
    private InvoiceRepository repository;

    @Override
    public Invoice create(Invoice invoice) {
        return repository.save(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        if (!repository.existsById(invoice.getInvoiceID()))
            return null;

        return repository.save(invoice);
    }

    @Override
    public boolean delete(Invoice invoice) {
        if (!repository.existsById(invoice.getInvoiceID()))
            return false;

        repository.delete(invoice);
        return true;
    }

    @Override
    public Invoice retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Invoice> retrieveAll() {
        return repository.findAll();
    }
}
