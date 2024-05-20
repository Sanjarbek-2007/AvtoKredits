package uz.yusa.avtokredits.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.yusa.avtokredits.domain.Payment;
import uz.yusa.avtokredits.domain.PaymentTimeTable;
import uz.yusa.avtokredits.domain.post.CreditTarrif;
import uz.yusa.avtokredits.repository.PaymentRepository;
import uz.yusa.avtokredits.repository.PaymentTimeTableRepository;
import uz.yusa.avtokredits.repository.TimeTableRepository;
import uz.yusa.avtokredits.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TimeTableService {
    public final TimeTableRepository timeTableRepository;
    public final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTimeTableRepository paymentTimeTableRepository;

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
    private void createPaymentTimeTable(CreditTarrif tarrif) {
        double price = tarrif.getPrice();
        int countMonths = tarrif.getCountMonths();
        double procents = tarrif.getProcents();

        double monthlyInterestRate = procents / 100 / 12;

// Calculate the monthly payment
        double monthlyPayment = (price * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -countMonths));

// Round the monthly payment to two decimal places
        monthlyPayment = Math.round(monthlyPayment * 100.0) / 100.0;
        PaymentTimeTable paymentTimeTable = new PaymentTimeTable();
        List<Payment> payments = new ArrayList<>();
        for (int i = 0; i < countMonths; i++) {
            Date date = new Date();

            // Convert Date to Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Set day of the month
            calendar.set(Calendar.DAY_OF_MONTH, new Date().getDay());

            // Set month (Note: January is 0)
            calendar.set(Calendar.MONTH, new Date().getMonth());

            // Convert Calendar back to Date
            Date modifiedDate = calendar.getTime();

            Payment payment = new Payment();
            payment.setAmount(monthlyPayment);
            payment.setPaymentDate(new Date());
            paymentRepository.save(payment);
        }

        paymentTimeTable.setPayments(payments);
        paymentTimeTableRepository.save(paymentTimeTable);
        System.out.println("Monthly payment: " + monthlyPayment);

    }

}
