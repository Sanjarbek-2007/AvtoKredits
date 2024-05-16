package uz.yusa.avtokredits.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String email;


    private LocalDateTime expiryDate;
    public VerificationToken(String email, String token) {
        this.email = email;
        this.token = token;
        // Set expiry date to current date and time plus 20 minutes
        this.expiryDate = LocalDateTime.now().plus(20, ChronoUnit.MINUTES);
    }

    // Getters and setters
}
