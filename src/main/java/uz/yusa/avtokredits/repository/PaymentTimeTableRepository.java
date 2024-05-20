package uz.yusa.avtokredits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yusa.avtokredits.domain.PaymentTimeTable;

public interface PaymentTimeTableRepository extends JpaRepository<PaymentTimeTable, Long> {
}
