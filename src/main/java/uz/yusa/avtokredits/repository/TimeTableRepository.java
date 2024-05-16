package uz.yusa.avtokredits.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yusa.avtokredits.domain.PaymentTimeTable;
import uz.yusa.avtokredits.domain.User;

@Repository
public interface TimeTableRepository extends JpaRepository<PaymentTimeTable, Long> {


    List<PaymentTimeTable> findByApplication_Customer_Email(String email);


}