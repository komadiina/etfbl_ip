package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Breakdown;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.repository.BreakdownRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreakdownService implements CRUDService<Breakdown> {
    @Autowired
    private BreakdownRepository repository;

    @Autowired
    private TransportationDeviceService transportationDeviceService;

    @Override
    public Breakdown create(Breakdown breakdown) {
        TransportationDevice td = transportationDeviceService.retrieve(breakdown.getDeviceID());

        if (td == null)
            return null;

        td.setStatus("Broken");
        transportationDeviceService.update(td);
        breakdown.setActive(true);

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

    @Override
    public List<Breakdown> retrieveAll() {
        return repository.findAll();
    }

    public Object retrieveByDeviceID(int deviceID) {
        return repository.findByAllDeviceID(deviceID);
    }

    public Breakdown disableBreakdown(int deviceID) {
        // update device
        TransportationDevice td = transportationDeviceService.retrieve(deviceID);
        td.setStatus("Available");
        transportationDeviceService.update(td);

        // disable in breakdowns table
        Breakdown breakdown = repository.findByDeviceIDActive(deviceID);
        breakdown.setActive(false);

        return update(breakdown);
    }

    public List<Object> removeBreakdown(int breakdownID) throws Exception {
        if (!repository.existsById(breakdownID))
            throw new Exception("Breakdown does not exist.");
        Breakdown breakdown = repository.findById(breakdownID).get();
        TransportationDevice device = transportationDeviceService.retrieve(breakdown.getDeviceID());

        if (device == null)
            throw new Exception("Device does not exist.");

        device.setStatus("Available");
        device = transportationDeviceService.update(device);
        breakdown.setActive(false);
        breakdown = update(breakdown);

        System.out.println(device);
        System.out.println(breakdown);
        return List.of(device, breakdown);
    }
}
