package uz.yusa.avtokredits.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.User;
import uz.yusa.avtokredits.service.AdminService;
import uz.yusa.avtokredits.service.ApplicationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/panel")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    private final ApplicationService applicationService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/applications/{id}/close")
    public ResponseEntity<String> closeApplicationById(@PathVariable Long id){
        applicationService.closeApplicationById(id);
        return ResponseEntity.ok("Application closed");
    }




}
