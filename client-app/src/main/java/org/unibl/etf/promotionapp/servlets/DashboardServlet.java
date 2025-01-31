package org.unibl.etf.promotionapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.unibl.etf.promotionapp.db.mysql.TransportationDeviceRepository;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransportationDeviceRepository tdRepository = new TransportationDeviceRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        tdRepository.open();
        req.getSession().setAttribute("transportationDevices", tdRepository.findAllAvailable());
        req.getSession().setAttribute("vehicles", tdRepository.findVehicles());
//        tdRepository.close();

        String address = "/WEB-INF/views/dashboard.jsp";
        req.getSession().setAttribute("message", null);
        getServletContext().getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "/WEB-INF/views/dashboard.jsp";
        req.getSession().setAttribute("message", null);

        String pageRequest = req.getParameter("pageRequest");
        if (pageRequest != null && pageRequest.equals("logout")) {
            req.getSession().invalidate();
            req.getSession().removeAttribute("userBean");

            resp.sendRedirect("login");
            return;
        } else if (pageRequest != null && pageRequest.equals("rentCar")) {
            resp.sendRedirect("rent-car");
            return;
        } else if (pageRequest != null && pageRequest.equals("rentBike")) {
            resp.sendRedirect("rent-bike");
            return;
        } else if (pageRequest != null && pageRequest.equals("rentScooter")) {
            resp.sendRedirect("rent-scooter");
            return;
        } else if (pageRequest != null && pageRequest.equals("editProfile")) {
            resp.sendRedirect("profile");
            return;
        }

        getServletContext().getRequestDispatcher(address).forward(req, resp);
    }
}
