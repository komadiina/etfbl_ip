package org.unibl.etf.promotionapp.db.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.unibl.etf.promotionapp.beans.RentalInfo;
import org.unibl.etf.promotionapp.beans.UserBean;
import org.unibl.etf.promotionapp.db.models.Client;
import org.unibl.etf.promotionapp.db.models.Rental;
import org.unibl.etf.promotionapp.db.models.RentalPrice;
import org.unibl.etf.promotionapp.db.models.TransportationDevice;
import org.unibl.etf.promotionapp.db.mysql.ClientRepository;
import org.unibl.etf.promotionapp.db.mysql.RentalPriceRepository;
import org.unibl.etf.promotionapp.db.mysql.RentalRepository;
import org.unibl.etf.promotionapp.db.mysql.TransportationDeviceRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class RentalService {
    private static ClientRepository clientRepository = new ClientRepository();
    public static void downloadPDF(int deviceID, RentalPriceRepository rentalPriceRepository,
                                   TransportationDeviceRepository tdRepository,
                                   HttpServletRequest request, HttpServletResponse response,
                                    Rental rental) throws ServletException, IOException {
        Client client = clientRepository.findByUsername(((UserBean)request.getSession().getAttribute("userBean")).getUsername());
        RentalInfo rentalInfo = new RentalInfo(rental);
        rentalInfo.setDevice(tdRepository.findByDeviceID(deviceID));
        rentalInfo.setPrice(rentalPriceRepository.findByDeviceID(deviceID));
        rentalInfo.setRentalID(rental.getRentalID());
        request.getSession().setAttribute("rentalInfo", rentalInfo);
        String fileName = client.getFirstName() + "_" + client.getLastName() + "_" + rental.getRentalID() + ".pdf";
        PDFService.generatePDF(client, rentalInfo, fileName, request, response);
    }

    public static void startRental(RentalRepository rentalRepository, TransportationDeviceRepository tdRepository,
            RentalPriceRepository rentalPriceRepository,
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int deviceID = Integer.parseInt(request.getParameter("deviceID"));
        request.getSession().setAttribute("deviceID", deviceID);

        int dropoffX = Integer.parseInt(request.getParameter("dropoffX"));
        int dropoffY = Integer.parseInt(request.getParameter("dropoffY"));
        String driversLicense = request.getParameter("driversLicense");
        String paymentCard = request.getParameter("paymentCard");

        if (paymentCard == null || paymentCard.isBlank() || paymentCard.isEmpty()) {
            request.getSession().setAttribute("message", "Please enter a valid payment card number.");
            return;
        } else if (driversLicense == null || driversLicense.isBlank() || driversLicense.isEmpty()) {
            request.getSession().setAttribute("message", "Please enter a valid driver's license ID.");
            return;
        }

        Rental lastRental = rentalRepository.findLastRentalByDeviceID(deviceID);
        int pickupX = (lastRental != null) ? lastRental.getDropoffX() : 0;
        int pickupY = (lastRental != null) ? lastRental.getDropoffY() : 0;

        // Create new rental
        Rental newRental = new Rental();
        newRental.setDeviceID(deviceID);
        newRental.setClientID(((UserBean)request.getSession().getAttribute("userBean")).getId());
        newRental.setPriceID(rentalPriceRepository.findByDeviceID(deviceID).getPriceID());
        newRental.setPickupX(pickupX);
        newRental.setPickupY(pickupY);
        newRental.setDropoffX(dropoffX);
        newRental.setDropoffY(dropoffY);
        newRental.setStartDateTime(LocalDateTime.now());
        newRental.setPaymentCard(paymentCard);
        newRental.setDriversLicense(driversLicense);

        rentalRepository.save(newRental);

        // update deviceID.status to 'Rented'
        tdRepository.updateStatus(deviceID, "Rented");
        tdRepository.updatePosition(deviceID, dropoffX, dropoffY);

        request.setAttribute("rentalStarted", true);
        request.setAttribute("rentalEnded", false);
        request.getRequestDispatcher("/WEB-INF/views/rent-car.jsp").forward(request, response);
    }

    public static void stopRental(RentalRepository rentalRepository, TransportationDeviceRepository tdRepository,
                                   HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        int clientID = ((UserBean)request.getSession().getAttribute("userBean")).getId();
        Rental activeRental = rentalRepository.findActiveRentalByClientID(clientID);
        System.out.println(activeRental);

        if (activeRental != null) {
            activeRental.setEndDateTime(LocalDateTime.now());

            // malo ai nije od zla
            long durationSeconds = ChronoUnit.SECONDS.between(activeRental.getStartDateTime(), activeRental.getEndDateTime());
            activeRental.setDuration((int) durationSeconds);

            rentalRepository.update(activeRental);

            request.setAttribute("rentalStarted", false);
            request.setAttribute("rentalEnded", true);

            // update posX and posY of transportationdevices.deviceID
            int deviceID = activeRental.getDeviceID();
            int dropoffX = activeRental.getDropoffX();
            int dropoffY = activeRental.getDropoffY();
            tdRepository.updatePosition(deviceID, dropoffX, dropoffY);
            tdRepository.updateStatus(deviceID, "Available");
        }

        request.getRequestDispatcher("/WEB-INF/views/rent-car.jsp").forward(request, response);
    }

    public static List<RentalInfo> getRentalInfo(RentalRepository rentalRepository, TransportationDeviceRepository transportationDeviceRepository,
            RentalPriceRepository rentalPriceRepository, HttpServletRequest request, HttpServletResponse response) {
        List<Rental> rentals = rentalRepository.findAllByClientID(((UserBean)request.getSession().getAttribute("userBean")).getId());
        List<TransportationDevice> devices = transportationDeviceRepository.findAll();
        List<RentalPrice> prices = rentalPriceRepository.findAll();

        return rentals.stream().map(rental -> {
            TransportationDevice device = devices.stream().filter(d -> d.getDeviceID() == rental.getDeviceID()).findFirst().orElse(null);
            RentalPrice price = prices.stream().filter(p -> p.getPriceID() == rental.getPriceID()).findFirst().orElse(null);

            RentalInfo info = new RentalInfo(rental);
            info.setDevice(device);
            info.setPrice(price);
            return info;
        }).toList();
    }
}
