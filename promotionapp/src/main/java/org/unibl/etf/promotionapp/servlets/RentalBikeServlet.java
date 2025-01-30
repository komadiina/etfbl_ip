package org.unibl.etf.promotionapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.unibl.etf.promotionapp.db.models.Rental;
import org.unibl.etf.promotionapp.db.mysql.RentalPriceRepository;
import org.unibl.etf.promotionapp.db.mysql.RentalRepository;
import org.unibl.etf.promotionapp.db.mysql.TransportationDeviceRepository;
import org.unibl.etf.promotionapp.db.services.RentalService;

import java.io.IOException;

import static org.unibl.etf.promotionapp.db.services.RentalService.startRental;
import static org.unibl.etf.promotionapp.db.services.RentalService.stopRental;

@WebServlet("/rent-bike")
public class RentalBikeServlet extends HttpServlet {
    private TransportationDeviceRepository tdRepository = new TransportationDeviceRepository();
    private RentalRepository rentalRepository = new RentalRepository();
    private RentalPriceRepository rentalPriceRepository = new RentalPriceRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("electricBicycles", tdRepository.findBicycles());
        request.getRequestDispatcher("/WEB-INF/views/rent-bike.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rentalAction = request.getParameter("rentalAction");

        if ("start".equals(rentalAction)) {
            startRental(rentalRepository, tdRepository, rentalPriceRepository, request, response);
        } else if ("stop".equals(rentalAction)) {
            stopRental(rentalRepository, tdRepository, request, response);
        } else if ("requestPDF".equals(rentalAction)) {
            int deviceID = Integer.parseInt(request.getSession().getAttribute("deviceID").toString());
            Rental lastRental = rentalRepository.findLastRentalByDeviceID(deviceID);
            RentalService.downloadPDF(deviceID, rentalPriceRepository, tdRepository, request, response, lastRental);
        }
    }
}
