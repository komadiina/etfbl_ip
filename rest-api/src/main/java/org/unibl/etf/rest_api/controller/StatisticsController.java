package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.service.StatisticsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/breakdowns")
    public ResponseEntity<?> getBreakdowns() {
        // broj kvarova po prevoznom sredstvu
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("breakdowns", statisticsService.getBreakdowns());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/incomes")
    public ResponseEntity<?> getIncomes(@RequestParam(name = "month") int month, @RequestParam(name = "year") int year) {
        // grafikon ukupnog prihoda po danima za izabrani mjesec
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("incomes", statisticsService.getIncomes(month, year));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/incomes/overall")
    public ResponseEntity<?> getIncomesByVehicleType() {
        // grafikon ukupnih prihoda po vrsti prevoznog sredstva
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("incomes", statisticsService.getIncomesByVehicleType());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
