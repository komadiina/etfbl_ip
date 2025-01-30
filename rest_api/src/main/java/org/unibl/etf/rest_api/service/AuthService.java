package org.unibl.etf.rest_api.service;

import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Client;
import org.unibl.etf.rest_api.model.db.Employee;
import org.unibl.etf.rest_api.model.db.User;
import org.unibl.etf.rest_api.model.dto.RegisterDto;
import org.unibl.etf.rest_api.service.crud.ClientService;
import org.unibl.etf.rest_api.service.crud.EmployeeService;
import org.unibl.etf.rest_api.service.crud.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    public User register(RegisterDto dto) {
        Client client = new Client();
        client.setIdCardNumber(dto.getIdCardNumber());
        client.setPassportID(dto.getPassportID());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setUsername(dto.getUsername());
        client.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        client.setUserType("Client");
        client.setActive(true);

        // save subclass and superclass
        clientService.create(client);
        userService.create(client);

        return client.obscure();
    }

    public User loginEmployee(String username, String password) throws Exception {
        User user = userService.retrieve(username);
        if (user == null)
            throw new Exception("User not found");

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword()))
            throw new Exception("Invalid credentials.");

        if (!user.isActive())
            throw new Exception("Account deactivated.");

        Employee employee = employeeService.retrieve(user.getId());
        if (employee == null)
            throw new Exception("User is not an employee.");

        return employee.obscure();
    }

    public Client loginClient(String username, String password) throws Exception {
        User user = userService.retrieve(username);
        if (user == null)
            throw new Exception("User not found");

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword()))
            throw new Exception("Invalid credentials.");

        if (!user.isActive())
            throw new Exception("Account deactivated.");

        Client client = clientService.retrieve(user.getId());
        if (client == null)
            throw new Exception("User is not a client.");

        return (Client)client.obscure();
    }

    public Client registerClient(RegisterDto registerDto) {
        Client client = new Client();
        client.setIdCardNumber(registerDto.getIdCardNumber());
        client.setPassportID(registerDto.getPassportID());
        client.setFirstName(registerDto.getFirstName());
        client.setLastName(registerDto.getLastName());
        client.setEmail(registerDto.getEmail());
        client.setPhoneNumber(registerDto.getPhoneNumber());
        client.setUsername(registerDto.getUsername());
        client.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        client.setUserType("Client");
        client.setActive(true);

        return clientService.create(client);
    }
}
