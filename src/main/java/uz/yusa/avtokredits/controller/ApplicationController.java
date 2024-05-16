package uz.yusa.avtokredits.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.repository.ApplicationRepository;
import uz.yusa.avtokredits.service.ApplicationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apps")
public class ApplicationController {
    private final ApplicationService applicationService;
    @GetMapping
    public ResponseEntity<List<Application>> getApplicationsByUsers() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }
}
