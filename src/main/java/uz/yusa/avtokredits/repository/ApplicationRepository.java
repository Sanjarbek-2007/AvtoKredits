package uz.yusa.avtokredits.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.yusa.avtokredits.domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Transactional
    @Modifying
    @Query("update Application a set a.isAccepted = ?1 where a.id = ?2")
    int updateIsAcceptedById(Boolean isAccepted, Long id);

    @Transactional
    @Modifying
    @Query("update Application a set a.isClosed = ?1 where a.id = ?2")
    int updateIsClosedById(Boolean isClosed, Long id);




}
