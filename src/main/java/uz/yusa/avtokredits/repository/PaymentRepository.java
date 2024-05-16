package uz.yusa.avtokredits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yusa.avtokredits.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}