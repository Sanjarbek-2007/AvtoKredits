package uz.yusa.avtokredits.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import uz.yusa.avtokredits.config.security.JwtProvider;
import uz.yusa.avtokredits.config.security.JwtResponse;
import uz.yusa.avtokredits.domain.User;
import uz.yusa.avtokredits.dto.LoginDto;
import uz.yusa.avtokredits.dto.SignupDto;
import uz.yusa.avtokredits.repository.RoleRepository;
import uz.yusa.avtokredits.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User getUserEntity(SignupDto dto){
        System.out.println(dto.email());
        return User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .username(dto.username())
                .build();
    }

    public JwtResponse signup(SignupDto dto){
        User user = getUserEntity(dto);

        if (user != null) {
            user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
            userRepository.save(user);


                String token = jwtProvider.generate(user);
                return new JwtResponse(token);
            }
        return null;
    }



    public JwtResponse login(LoginDto loginDto){
        Optional<User> user = userRepository.findByEmail(loginDto.email());
        if (user.isPresent() && passwordEncoder.matches(loginDto.password(), user.get().getPassword())){
            String token = jwtProvider.generate(user.orElse(null));
            return new JwtResponse(token);
        }
        return null;
    }

}
