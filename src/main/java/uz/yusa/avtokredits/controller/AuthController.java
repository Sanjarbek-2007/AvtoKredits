package uz.yusa.avtokredits.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yusa.avtokredits.config.security.JwtResponse;
import uz.yusa.avtokredits.domain.User;
import uz.yusa.avtokredits.dto.ConfirmDto;
import uz.yusa.avtokredits.dto.LoginDto;
import uz.yusa.avtokredits.dto.SignupDto;
import uz.yusa.avtokredits.dto.TokenInConfirmationDto;
import uz.yusa.avtokredits.repository.RoleRepository;
import uz.yusa.avtokredits.repository.UserRepository;
import uz.yusa.avtokredits.service.AuthService;
import uz.yusa.avtokredits.service.ConfirmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    private final ConfirmService confirmService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto dto){
        return ResponseEntity.ok(authService.signup(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

//
//    @GetMapping("/verify")
//    public String verify(@RequestBody TokenInConfirmationDto dto){
//        System.out.println(dto.email()+" Is verifying ");
//        return confirmService.verify(dto);
//    }
//
//
//
//    @PostMapping("/ask")
//    public ResponseEntity<String> askToVerify(@RequestBody ConfirmDto dto){
//        return ResponseEntity.ok(confirmService.askToVerify(dto.email()));
//    }



}
