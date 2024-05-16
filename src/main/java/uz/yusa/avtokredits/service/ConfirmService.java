package uz.yusa.avtokredits.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yusa.avtokredits.config.security.JwtProvider;
import uz.yusa.avtokredits.config.security.JwtResponse;
import uz.yusa.avtokredits.domain.User;
import uz.yusa.avtokredits.domain.VerificationToken;
import uz.yusa.avtokredits.dto.LoginDto;
import uz.yusa.avtokredits.dto.TokenInConfirmationDto;
import uz.yusa.avtokredits.repository.UserRepository;
import uz.yusa.avtokredits.repository.VerificationTokenRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthService authService;
    private final MailService mailService;
    private final JwtProvider jwtProvider;

    @Transactional
    public String   verify(TokenInConfirmationDto dto) {
        Optional<User> byEmail = userRepository.findByEmail(dto.email());
        System.out.println(byEmail.get().toString());
//        if(byEmail.isPresent()) {
            String token = dto.token();
            Optional<VerificationToken> tokenVer = verificationTokenRepository.findByEmail(dto.email());
        System.out.println(tokenVer.get().toString());
        User user = byEmail.get();
        userRepository.updateConfirmedById(Boolean.TRUE, user.getId());


        verificationTokenRepository.deleteByEmail(dto.email());

        return "Succesfully confirmed";
        //        }

    }

    public String askToVerify(String email) {
        String token = jwtProvider.generateVerificationToken(  email );
        VerificationToken verToken = new VerificationToken(email, token);
        verificationTokenRepository.save(verToken);

        String link = "http://localhost:8080/auth/verify?token=" + token+ "&email="+email;
        String text="You are gonna be verified in Railway Selling platform as user : "+email+"\n"
                        +" click the link to verify your account :  "+link+"\n"+token;

            mailService.sendMail(email, "Verifiction", text);






        return "Confirmation link sent to your account : "+email+"\n";

    }
}
