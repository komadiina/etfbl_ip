package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Employee;
import org.unibl.etf.rest_api.repository.EmployeeRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements CRUDService<Employee> {
    @Autowired
    private EmployeeRepository repository;

    @Override
    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (!repository.existsById(employee.getId()))
            return null;

        return repository.save(employee);
    }

    @Override
    public boolean delete(Employee employee) {
        if (!repository.existsById(employee.getId()))
            return false;

        repository.delete(employee);
        return true;
    }

    @Override
    public Employee retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> retrieveAll() {
        return repository.findAll();
    }

    public Page<Employee> retrieveAllPaginated(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
}
