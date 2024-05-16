package uz.yusa.avtokredits.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.yusa.avtokredits.domain.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

//    long deleteByToken(String token);


    void deleteByToken(String token);
    void deleteByEmail(String email);

    Optional<VerificationToken> findByEmail(String token);
}