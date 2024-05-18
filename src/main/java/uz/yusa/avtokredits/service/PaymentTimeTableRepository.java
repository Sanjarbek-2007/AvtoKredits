package uz.yusa.avtokredits.service;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yusa.avtokredits.domain.PaymentTimeTable;

public interface PaymentTimeTableRepository extends JpaRepository<PaymentTimeTable, Long> {
}
