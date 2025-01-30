package org.unibl.etf.rest_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Breakdown;
import org.unibl.etf.rest_api.model.db.Rental;
import org.unibl.etf.rest_api.model.db.RentalPrice;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.service.crud.BreakdownService;
import org.unibl.etf.rest_api.service.crud.RentalPriceService;
import org.unibl.etf.rest_api.service.crud.RentalService;
import org.unibl.etf.rest_api.service.crud.TransportationDeviceService;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class StatisticsService {
    @Autowired
    private BreakdownService breakdownService; // za kvarove

    @Autowired
    private TransportationDeviceService transportationDeviceService; // uopsteno

    @Autowired
    private RentalService rentalService; // za prihode

    @Autowired
    private RentalPriceService rentalPriceService; // za prihode

    public Map<String, Integer> getBreakdowns() {
        // broj kvarova po kategoriji prevoznog sredstva
        Map<String, Integer> result = new HashMap<>();
        result.put("Vehicle", 0);
        result.put("ElectricScooter", 0);
        result.put("ElectricBicycle", 0);

        breakdownService.retrieveAll().forEach(breakdown -> {
            TransportationDevice td = transportationDeviceService.retrieve(breakdown.getDeviceID());

            if (td != null)
                result.put(td.getDeviceType(), result.get(td.getDeviceType()) + 1);
        });

        return result;
    }

    public Object getIncomes(int month, int year) {
        // grafikon ukupnog prihoda po danima za izabrani mjesec
        Map<String, Double> result = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));

        YearMonth ym = YearMonth.of(year, month);
        int days = ym.lengthOfMonth();

        for (int i = 1; i <= days; i++)
            result.put(String.valueOf(i), 0.0);

        rentalService.retrieveAll().forEach(rental -> {
            LocalDateTime startDate = rental.getStartDateTime();

            if (startDate.getMonth().getValue() == month && startDate.getYear() == year) {
                int day = startDate.getDayOfMonth();
                Double ppm = rentalPriceService.retrieve(rental.getPriceID()).getPricePerMinute();
                Double totalPrice = ppm * (rental.getDuration() / 60.0);

                result.put(String.valueOf(day), result.get(String.valueOf(day)) + totalPrice);
            }
        });

//        System.out.println(result.values().stream().toList());
        return result.values().stream().toList();
    }

    public Object getIncomesByVehicleType() {
        // grafikon ukupnih prihoda po vrsti prevoznog sredstva
        Map<String, Double> result = new HashMap<>();

        result.put("Vehicle", 0.0);
        result.put("ElectricScooter", 0.0);
        result.put("ElectricBicycle", 0.0);

        rentalService.retrieveAll().forEach(rental -> {
            TransportationDevice td = transportationDeviceService.retrieve(rental.getDeviceID());
            Double ppm = rentalPriceService.retrieve(rental.getPriceID()).getPricePerMinute();
            Double totalPrice = ppm * (rental.getDuration() / 60.0);

            if (td != null)
                result.put(td.getDeviceType(), result.get(td.getDeviceType()) + totalPrice);
        });

        return result;
    }
}
