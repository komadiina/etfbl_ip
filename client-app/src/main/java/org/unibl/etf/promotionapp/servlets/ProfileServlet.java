package org.unibl.etf.promotionapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jdk.jfr.Frequency;
import org.unibl.etf.promotionapp.beans.RentalInfo;
import org.unibl.etf.promotionapp.beans.UserBean;
import org.unibl.etf.promotionapp.db.models.Rental;
import org.unibl.etf.promotionapp.db.models.RentalPrice;
import org.unibl.etf.promotionapp.db.models.TransportationDevice;
import org.unibl.etf.promotionapp.db.mysql.ClientRepository;
import org.unibl.etf.promotionapp.db.mysql.RentalPriceRepository;
import org.unibl.etf.promotionapp.db.mysql.RentalRepository;
import org.unibl.etf.promotionapp.db.mysql.TransportationDeviceRepository;
import org.unibl.etf.promotionapp.db.services.RentalService;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private RentalRepository rentalRepository = new RentalRepository();
    private TransportationDeviceRepository transportationDeviceRepository = new TransportationDeviceRepository();
    private RentalPriceRepository rentalPriceRepository = new RentalPriceRepository();
    private ClientRepository clientRepository = new ClientRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/views/profile.jsp";
        request.getSession().setAttribute("message", null);

        // load rental history into RentalInfo
        request.getSession().setAttribute("rentalInfo", null);
        List<RentalInfo> rentalInfoList = RentalService.getRentalInfo(rentalRepository, transportationDeviceRepository, rentalPriceRepository, request, response);
        request.getSession().setAttribute("rentalInfoHistory", rentalInfoList);
        getServletContext().getRequestDispatcher(address).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("editUsername")) {
                String username = ((UserBean)request.getSession().getAttribute("userBean")).getUsername();
                String newUsername = request.getParameter("username");
                clientRepository.editUsername(username, newUsername);

                UserBean current = (UserBean)request.getSession().getAttribute("userBean");
                current.setUsername(newUsername);
                request.getSession().setAttribute("userBean", current);

                response.sendRedirect("profile");
            } else if (action.equals("editPassword")) {
                String username = ((UserBean)request.getSession().getAttribute("userBean")).getUsername();
                String newPassword = request.getParameter("password");
                clientRepository.editPassword(username, newPassword);
                response.sendRedirect("profile");
            }
        } else {
            request.getSession().setAttribute("message", "Invalid action - check your defined username/password and try again.");
        }
    }
}
