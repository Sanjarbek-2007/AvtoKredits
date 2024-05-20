package uz.yusa.avtokredits.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.dto.ApplicationSaveDto;
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


    @PostMapping("/add")
    public ResponseEntity<ApplicationSaveDto> saveApplication(@RequestBody ApplicationSaveDto application) {
        try {
            ApplicationSaveDto savedApplication = applicationService.saveApplication(application);
            return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/accept/{id}")
    public ResponseEntity<Application> acceptApplication(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.acceptApp(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}" )
    public ResponseEntity<Application> getApplicationUsers(@PathVariable Long id) {

        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/id/admin")
//    public ResponseEntity<Application> getApplicationAdmins(@PathVariable Long id) {
//
//        return ResponseEntity.ok(applicationService.getApplication(id));
//    }


}
