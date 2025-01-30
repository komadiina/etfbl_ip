package org.unibl.etf.promotionapp.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.unibl.etf.promotionapp.db.mysql.ClientRepository;
import org.unibl.etf.promotionapp.rest.RESTUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static boolean anyNull(String... list) {
        for (String s : list)
            if (s == null || s.isEmpty() || s.isBlank())
                return true;

        return false;
    }

    private ClientRepository clientRepository = new ClientRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/views/register.jsp";
        request.getSession().setAttribute("message", null);
        getServletContext().getRequestDispatcher(address).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/views/register.jsp";
        request.getSession().setAttribute("message", null);

        // fetch parameters defined in register.jsp
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phoneNumber");
        String idCardNumber = request.getParameter("idCardNumber");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String passportID = request.getParameter("passportID");

        if (anyNull(username, password, email, phone, firstName, lastName)) {
            request.getSession().setAttribute("message", "Please fill in all required fields.");
            getServletContext().getRequestDispatcher(address).forward(request, response);
            return;
        }

//        if (RESTUtil.register(username, password, phone, email, idCardNumber, firstName, lastName, passportID)) {
        if (clientRepository.register(username, password, phone, email, idCardNumber, firstName, lastName, passportID) != null) {
            request.getSession().setAttribute("message", "Account created successfully. You can now sign in.");
            response.sendRedirect("login");
            return;
        }

        getServletContext().getRequestDispatcher(address).forward(request, response);
    }
}
