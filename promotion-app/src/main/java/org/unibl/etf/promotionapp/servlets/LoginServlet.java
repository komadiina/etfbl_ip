package org.unibl.etf.promotionapp.servlets;

import org.unibl.etf.promotionapp.db.models.Employee;
import org.unibl.etf.promotionapp.db.mysql.EmployeeRepository;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private EmployeeRepository employeeRepository = new EmployeeRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/views/login.jsp";
        request.getRequestDispatcher(address).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || username.isBlank()) {
            request.getSession().setAttribute("message", "Username cannot be empty");
        } else if (password == null || password.isEmpty() || password.isBlank()) {
            request.getSession().setAttribute("message", "Password cannot be empty");
        } else {
            System.out.println(employeeRepository.readAll());
            Employee result = employeeRepository.login(username, password);
            if (result == null) {
                request.getSession().setAttribute("message", "Invalid credentials.");
                response.sendRedirect("login");
                return;
            }

            request.getSession().setAttribute("user", result);
            response.sendRedirect("dashboard");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}
