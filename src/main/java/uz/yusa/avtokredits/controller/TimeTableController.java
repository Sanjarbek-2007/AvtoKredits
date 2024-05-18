package uz.yusa.avtokredits.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yusa.avtokredits.domain.PaymentTimeTable;
import uz.yusa.avtokredits.service.TimeTableService;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PaymentTimeTable>> getAllTimeTables() {
        return ResponseEntity.ok(timeTableService.getAllUsersPaymentTimeTables());
    }
    @GetMapping("/{paymentTableId}")
    public ResponseEntity<PaymentTimeTable> getPaymentTable(@PathVariable Long paymentTableId) {

        return ResponseEntity.ok(timeTableService.getPaymentTimeTable(paymentTableId));
    }
}
