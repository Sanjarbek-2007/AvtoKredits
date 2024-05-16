package uz.yusa.avtokredits.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.yusa.avtokredits.domain.PaymentTimeTable;
import uz.yusa.avtokredits.repository.TimeTableRepository;
import uz.yusa.avtokredits.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TimeTableService {
    public final TimeTableRepository timeTableRepository;
    public final UserRepository userRepository;

    public PaymentTimeTable createPaymentTimeTable() {
        return new PaymentTimeTable();
    }
    public List<PaymentTimeTable> getAllUsersPaymentTimeTables() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return timeTableRepository.findByApplication_Customer_Email(userEmail);
    }

    public PaymentTimeTable getPaymentTimeTable(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        PaymentTimeTable paymentTimeTable = timeTableRepository.findById(id).orElse(null);
        if (paymentTimeTable != null && paymentTimeTable.getApplication().getCustomer().getEmail().equals(userEmail)) {
            return paymentTimeTable;
        }else {
            return null;
        }
    }
}
