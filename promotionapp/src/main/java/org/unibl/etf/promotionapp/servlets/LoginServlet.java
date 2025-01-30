package org.unibl.etf.promotionapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.unibl.etf.promotionapp.beans.UserBean;
import org.unibl.etf.promotionapp.db.models.Client;
import org.unibl.etf.promotionapp.db.mysql.ClientRepository;
import org.unibl.etf.promotionapp.rest.RESTUtil;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 3L;
    private ClientRepository clientRepository = new ClientRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/views/login.jsp";
        request.getSession().setAttribute("message", null);
        getServletContext().getRequestDispatcher(address).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = "/WEB-INF/views/login.jsp";

        if (username.isBlank() || password.isBlank() || password.isEmpty() || username.isEmpty()) {
            request.getSession().setAttribute("message", "Invalid username or password.");
        } else {
            Client client = clientRepository.login(username, password);
            if (client != null) {
                UserBean userBean = new UserBean();
                userBean.setEmail(client.getEmail());
                userBean.setFirstName(client.getFirstName());
                userBean.setLastName(client.getLastName());
                userBean.setId(client.getId());
                userBean.setPassportID(client.getPassportID());
                userBean.setPhoneNumber(client.getPhoneNumber());
                userBean.setUsername(client.getUsername());
                userBean.setIdCardNumber(client.getIdCardNumber());

                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("userBean", userBean);
                response.sendRedirect("dashboard");
                return;
            } else {
                request.getSession().setAttribute("message", "Invalid username or password.");
            }
        }

        getServletContext().getRequestDispatcher(address).forward(request, response);
    }
}
